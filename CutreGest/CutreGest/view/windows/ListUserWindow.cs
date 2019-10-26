using CSerpisAd.GtkHelper;
using Gtk;
using System;
using CutreGest.dao;
using CutreGest.model;

namespace CutreGest.view.windows {
    public partial class ListUserWindow : Window {

        private Window parent;
        private User currentUser;

        public ListUserWindow(User currentUser, Window parent) :base(WindowType.Toplevel) {
            this.currentUser = currentUser;
            this.parent = parent;
            buildWindow();
            setActions();
            fillTree();
            refreshActions();
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
            //Botón volver
            closeAction.Activated += (sender, e) => close();
            //Cambió la selección en el treeview
            tvUsers.Selection.Changed += (sender, e) => refreshActions();
            //Botón refrescar
            refreshAction.Activated += (sender, e) => fillTree();
            //Botón editar
            editAction.Activated += (sender, e) => {
                User selected = (User)TreeViewHelper.GetSelected(tvUsers);
                UserWindow userWindow = new UserWindow(currentUser, selected, this);
                userWindow.Destroyed += (innerSender, innerE) => fillTree();
            };
            //Botón borrar
            removeAction.Activated += (sender, e) => {
                User selected = (User)TreeViewHelper.GetSelected(tvUsers);
                if (selected.Id == currentUser.Id) {
                    DialogHelper.ShowDialog("Error!", "No puedes borrar el usuario\ncon el que estás trabajando",
                            this, DialogFlags.Modal | DialogFlags.DestroyWithParent, "Volver", ResponseType.Cancel);
                    return;
                }
                UserDAO.Delete(selected);
                fillTree();
            };
            //Botón añadir
            addAction.Activated += (sender, e) => {
                UserWindow userWindow = new UserWindow(currentUser, new User(), this);
                userWindow.Destroyed += (innerSender, innerE) => fillTree();
            };
            //Doble click en una línea del treeView
            tvUsers.RowActivated += (o, args) => {
                User selected = (User)TreeViewHelper.GetSelected(tvUsers);
                UserWindow userWindow = new UserWindow(currentUser, selected, this);
                userWindow.Destroyed += (sender, e) => fillTree();
            };
        }

        private void fillTree() {
            TreeViewHelper.Fill(tvUsers, new string[] { "Id", "Name", "IsAdmin" },
                UserDAO.GetAll());
        }

        private void refreshActions() {
            bool hasSelected = tvUsers.Selection.CountSelectedRows() > 0;
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
