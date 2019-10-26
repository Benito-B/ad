using System;
using Gtk;
using CutreGest.model;
using CutreGest.dao;
using CSerpisAd.GtkHelper;

namespace CutreGest.view.windows {
    public partial class ListArticleWindow : Window {

        private User user;

        public ListArticleWindow(User user) :base(WindowType.Toplevel) {
            this.user = user;
            Build();
            SetPosition(WindowPosition.CenterAlways);
            setActions();
            fillTree();
            refreshActions();
            usersAction.Visible = user.IsAdmin;
        }

        protected void OnDeleteEvent(object sender, DeleteEventArgs a) {
            Application.Quit();
            a.RetVal = true;
        }

        private void setActions() {
            //Botón de salir
            exitAction.Activated += (sender, e) => Application.Quit();
            //Botón de añadir artículo
            articleAddAction.Activated += (sender, e) => {
                ArticleWindow articleWindow = new ArticleWindow(new Article(), this, user);
                articleWindow.Destroyed += (innerSender, innerE) => fillTree();
            };
            //Evento de selección del treeview
            tvArticles.Selection.Changed += (sender, e) => refreshActions();
            //Botón refrescar
            articleRefreshAction.Activated += (sender, e) => fillTree();
            //Botón editar artículo
            articleEditAction.Activated += (sender, e) => {
                Article selectedArticle = (Article)TreeViewHelper.GetSelected(tvArticles);
                ArticleWindow articleWindow = new ArticleWindow(selectedArticle, this, user);
                articleWindow.Destroyed += (innerSender, innerE) => fillTree();
            };
            //Botón borrar artículo
            articleRemoveAction.Activated += (sender, e) => {
                Article selectedArticle = (Article)TreeViewHelper.GetSelected(tvArticles);
                ArticleDAO.Delete(selectedArticle);
                fillTree();
            };
            //Doble click en una línea del treeview
            tvArticles.RowActivated += (o, args) => {
                Article selectedArticle = (Article)TreeViewHelper.GetSelected(tvArticles);
                ArticleWindow articleWindow = new ArticleWindow(selectedArticle, this, user);
                articleWindow.Destroyed += (innerSender, innerE) => fillTree();
            };
            //Menú categorías
            categoriesAction.Activated += (sender, e) => new ListCategoryWindow(this);
            //Menú about
            aboutAction.Activated += (sender, e) => DialogHelper.ShowDialog("Cutre gest", 
                "Porque eres cutre pero quieres tener un software de gestión", this, 
                DialogFlags.DestroyWithParent |DialogFlags.Modal, "Volver", ResponseType.Close);
            //Menú usuarios
            usersAction.Activated += (sender, e) => new ListUserWindow(user, this);
            //Menú mostrar movimientos
            MostrarMovimientosAction.Activated += (sender, e) => new ListMovementWindow(this);
            //Menú añadir movimiento
            movementAddAction.Activated += (sender, e) => { 
                MovementWindow movementWindow = new MovementWindow(this);
                movementWindow.Destroyed += (innerSender, innerE) => fillTree();
             };

        }

        private void fillTree() {
            TreeViewHelper.Fill(tvArticles, new string[] { "Id", "Name", "Category", "Price", "Stock" }, 
                ArticleDAO.GetAll());
        }

        private void refreshActions() {
            bool hasSelected = tvArticles.Selection.CountSelectedRows() > 0;
            articleEditAction.Sensitive = hasSelected;
            articleRemoveAction.Sensitive = hasSelected;
        }

        protected override void OnDestroyed() {
            base.OnDestroyed();
            Application.Quit();
        }
    }
}
