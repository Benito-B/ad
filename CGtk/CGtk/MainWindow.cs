using System;
using Gtk;
using CGtk;
using CSerpisAd.GtkHelper;
using System.Collections.Generic;

namespace CGtk.Articulo {

    public partial class MainWindow : Gtk.Window {
        public MainWindow() : base(Gtk.WindowType.Toplevel) {
            Build();

            ICollection<Article> articles = ArticleDAO.GetAll();
            TreeViewHelper.Fill(treeView, new string[] { "Id", "Name", "Category", "Price" }, articles);
            treeView.Selection.Changed += (sender, e) => refreshActions();

            addAction.Activated += (sender, e) => new WArticle();
            createCategory.Activated += (sender, e) => {WCategoria wCategoria = new WCategoria(new Categoria());};

            refreshActions();
        }

        protected void OnDeleteEvent(object sender, DeleteEventArgs a) {
            Application.Quit();
            a.RetVal = true;
        }

        private void refreshActions() {
            bool hasSelected = treeView.Selection.CountSelectedRows() > 0;
            editAction.Sensitive = hasSelected;
            removeAction.Sensitive = hasSelected;
        }
    }
}