using System;
using CSerpisAd.GtkHelper;
using Gtk;
using CutreGest.dao;
using CutreGest.model;

namespace CutreGest.view.windows {
    public partial class ListCategoryWindow : Window {

        private Window parent;

        public ListCategoryWindow(Window parent) :base(WindowType.Toplevel) {
            this.parent = parent;
            buildWindow();
            setActions();
            fillTree();
        }

        private void buildWindow() {
            Build();
            parent.Sensitive = false;
            parent.KeepAbove = false;
            parent.KeepBelow = true;
            KeepAbove = true;
            SetPosition(WindowPosition.CenterAlways);
        }

        private void setActions() {
            //Botón para volver
            closeAction1.Visible = true;
            closeAction1.Activated += (sender, e) => close();
            //Botón añadir
            addAction.Activated += (sender, e) => {
                CategoryWindow categoryWindow = new CategoryWindow(new Category(), this);
                categoryWindow.Show();
                categoryWindow.Destroyed += (innerSender, innerE) => fillTree();
            };
            //Botón editar
            editAction.Activated += (sender, e) => {
                Category selected = (Category)TreeViewHelper.GetSelected(tvCategories);
                CategoryWindow categoryWindow = new CategoryWindow(selected, this);
                categoryWindow.Destroyed += (innerSender, innerE) => fillTree();
            };
            //Botón eliminar
            removeAction.Activated += (sender, e) => {
                Category selected = (Category)TreeViewHelper.GetSelected(tvCategories);
                CategoryDAO.Delete(selected);
                fillTree();
            };
            //Botón refrescar
            refreshAction.Activated += (sender, e) => fillTree();
            //Doble click en una fila
            tvCategories.RowActivated += (o, args) => {
                Category selected = (Category)TreeViewHelper.GetSelected(tvCategories);
                CategoryWindow categoryWindow = new CategoryWindow(selected, this);
                categoryWindow.Destroyed += (innerSender, innerE) => fillTree();
            };
            //Ha cambiado la fila seleccionada
            tvCategories.Selection.Changed += (sender, e) => refreshActions();
        }

        private void fillTree() {
            TreeViewHelper.Fill(tvCategories, new string[] { "Id", "Name" },
                CategoryDAO.GetAll());
        }

        private void refreshActions() {
            bool hasSelected = tvCategories.Selection.CountSelectedRows() > 0;
            editAction.Sensitive = hasSelected;
            removeAction.Sensitive = hasSelected;
        }

        private void close() {
            Destroy();
        }

        protected override void OnDestroyed() {
            parent.Sensitive = true;
            parent.KeepAbove = true;
            parent.KeepBelow = false;
            base.OnDestroyed();
        }
    }
}
