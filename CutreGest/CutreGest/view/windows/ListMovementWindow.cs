using System;
using Gtk;
using CSerpisAd.GtkHelper;
using CutreGest.dao;
namespace CutreGest.view.windows {
    public partial class ListMovementWindow : Window {

        private Window parent;

        public ListMovementWindow(Window parent) :base(WindowType.Toplevel) {
            this.parent = parent;
            buildWindow();
            TreeViewHelper.Fill(tvMovements, new string[] { "Article", "Type", "Ammount", "Date" },
                 MovementDAO.GetAll());

            btBack.Clicked += (sender, e) => close();
        }

        private void buildWindow() {
            Build();
            KeepAbove = true;
            parent.Sensitive = false;
            parent.KeepAbove = false;
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
