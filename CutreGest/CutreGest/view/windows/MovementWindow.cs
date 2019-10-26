using System;
using Gtk;
using System.Collections.Generic;
using CutreGest.model;
using CutreGest.dao;
using CutreGest.logic;

namespace CutreGest.view.windows {
    public partial class MovementWindow : Window {

        private Window parent;
        private List<Article> articles = new List<Article>();

        public MovementWindow(Window parent) :base(WindowType.Toplevel) {
            this.parent = parent;
            buildWindow();
            setActions();
        }

        private void buildWindow() {
            Build();
            KeepAbove = true;
            parent.Sensitive = false;
            parent.KeepAbove = false;
            populateCombo();
        }

        private void setActions() {
            btBack.Clicked += (sender, e) => close();
            btSubmit.Clicked += (sender, e) => {
                Movement m = new Movement();
                m.Article = articles[comboArticles.Active];
                m.Ammount = spinAmmount.ValueAsInt;
                m.Type = rbIn.Active ? Movement.M_IN : Movement.M_OUT;
                if(Warehouse.DoMovement(m, this))
                    close();
            };
        }

        private void populateCombo() {
            foreach(Article a in ArticleDAO.GetAll()) {
                comboArticles.AppendText(a.Name);
                articles.Add(a);
            }
            comboArticles.Active = 0;
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
