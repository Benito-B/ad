using System;
using CutreGest.model;
using Gtk;
using CutreGest.dao;
namespace CutreGest.view.windows {
    public partial class CategoryWindow : Window {

        Window parent;

        public CategoryWindow(Category c, Window parent) :base(WindowType.Toplevel) {
            this.parent = parent;
            buildWindow(c);
            addActions();
        }

        private void buildWindow(Category c) {
            Build();
            KeepAbove = true;
            parent.Sensitive = false;
            parent.KeepAbove = false;

            bool isVisible = c.Id > 0;
            labelId.Visible = isVisible;
            labelCategoryId.Visible = isVisible;

            if (isVisible)
                labelCategoryId.Text = c.Id.ToString();
            entryCategoryName.Text = c.Name ?? "";
        }

        private void addActions() {
            //Pulsar intro en el campo de categoría
            entryCategoryName.Activated += (sender, e) => btSubmit.Click();
            //Botón cancelar
            btBack.Clicked += (sender, e) => close();
            //Botón guardar
            btSubmit.Clicked += (sender, e) => {
                Category newCategory = new Category();
                newCategory.Name = entryCategoryName.Text;
                if (labelCategoryId.Visible)
                    newCategory.Id = ulong.Parse(labelCategoryId.Text);
                CategoryDAO.Save(newCategory);
                close();
            };
        }

        private void close() {
            Destroy();
        }

        protected override void OnDestroyed() {
            parent.Sensitive = true;
            parent.KeepAbove = true;
            base.OnDestroyed();
        }
    }
}
