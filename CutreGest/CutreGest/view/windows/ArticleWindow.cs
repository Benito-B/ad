using System.Collections.Generic;
using CutreGest.dao;
using CutreGest.model;
using Gtk;
using Stock = CutreGest.model.Stock;

namespace CutreGest.view.windows {

    public partial class ArticleWindow : Window {

        private User user;
        private Window parent;
        List<Category> categories;

        public ArticleWindow(Article article, Window parent, User user) :base(Gtk.WindowType.Toplevel) {
            this.user = user;
            this.parent = parent;
            categories = new List<Category>();
            buildWindow(article);

            btBack.Clicked += (sender, e) => close();
            btSubmit.Clicked += (sender, e) => {
                Article newArticle = buildArticleFromFields();
                ArticleDAO.Save(newArticle);
                close();
            };

        }

        private void buildWindow(Article article) {
            Build();
            KeepAbove = true;
            parent.KeepAbove = false;
            parent.Sensitive = false;
            toggleId(article);
            populateCategoryCombo(article.Id);
            spinCurrentStock.Sensitive = user.IsAdmin;
            spinProductPrice.Sensitive = user.IsAdmin;
            if(article.Id > 0) {
                entryProductName.Text = article.Name;
                spinProductPrice.Value = article.Price;
                spinCurrentStock.Value = article.Stock.CurrentStock;
                spinMinStock.Value = article.Stock.MinStock;
                spinMaxStock.Value = article.Stock.MaxStock;
            } else {
                spinMinStock.Value = 5;
                spinMaxStock.Value = 25;
            }

        }

        private Article buildArticleFromFields() {
            Article article = new Article();
            Stock stock = new Stock();
            article.Name = entryProductName.Text;
            article.Price = spinProductPrice.Value;
            article.Category = categories[cbProductCategory.Active].Id;

            stock.CurrentStock = spinCurrentStock.ValueAsInt;
            stock.MinStock = spinMinStock.ValueAsInt;
            stock.MaxStock = spinMaxStock.ValueAsInt;

            if(labelProductId.Visible) {
                article.Id = ulong.Parse(labelProductId.Text);
                stock.ArticleId = ulong.Parse(labelProductId.Text);
            }
            article.Stock = stock;
            return article;
        }

        private void toggleId(Article a) {
            bool isVisible = a.Id > 0;
            labelId.Visible = isVisible;
            labelProductId.Visible = isVisible;
            if (isVisible) {
                labelProductId.Text = a.Id.ToString();
            }
        }

        private void populateCategoryCombo(ulong id) {
            int x = 0;
            bool hasCategory = false;
            foreach (Category c in CategoryDAO.GetAll()) {
                categories.Add(c);
                cbProductCategory.AppendText(c.Name);
                if (c.Id == id) {
                    cbProductCategory.Active = x;
                    hasCategory = true;
                }
                x++;
            }
            //Si no tiene categoría seleccionada le pongo una
            if (!hasCategory)
                cbProductCategory.Active = 0;
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
