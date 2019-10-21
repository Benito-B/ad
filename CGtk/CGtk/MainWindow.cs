using System;
using Gtk;
using CGtk;
using CSerpisAd.GtkHelper;
using System.Collections.Generic;

namespace CGtk.Articulo {

    public partial class MainWindow : Gtk.Window {
        public MainWindow() : base(Gtk.WindowType.Toplevel) {
            Build();

            ICollection<Article> articles = new List<Article>();
            articles = ArticleDAO.GetArticles();

            TreeViewHelper.Fill(treeView, new string[] { "Id", "Name", "Category", "Price" }, articles);

            addAction.Activated += (sender, e) => new DialogCreateArticle();

            treeView.Selection.Changed += (sender, e) => refreshActions();

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