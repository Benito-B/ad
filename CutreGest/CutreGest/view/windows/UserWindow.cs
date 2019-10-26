using System;
using CutreGest.model;
using Gtk;
using CutreGest.dao;
namespace CutreGest.view.windows {
    public partial class UserWindow : Window {

        private Window parent;
        private User currentUser;

        public UserWindow(User currentUser, User user, Window parent) :base(WindowType.Toplevel) {
            this.parent = parent;
            this.currentUser = currentUser;
            buildWindow(user);
            setActions();
        }

        private void buildWindow(User u) {
            Build();
            KeepAbove = true;
            parent.Sensitive = false;
            parent.KeepAbove = false;

            bool isVisible = u.Id > 0;
            labelId.Visible = isVisible;
            labelUserId.Visible = isVisible;
            checkAdmin.Active = u.IsAdmin;
            bool editingCurrentUser = currentUser.Id != u.Id;
            checkAdmin.Sensitive = editingCurrentUser;

            if (isVisible)
                labelUserId.Text = u.Id.ToString();
            entryUsername.Text = u.Name ?? "";
        }

        private void setActions() {
            btBack.Clicked += (sender, e) => close();
            btSubmit.Clicked += (sender, e) => {
                User u = new User();
                u.Name = entryUsername.Text;
                u.IsAdmin = checkAdmin.Active;
                u.Password = entryUserPassword.Text;
                if (labelUserId.Visible)
                    u.Id = ulong.Parse(labelUserId.Text);

                UserDAO.Save(u);
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
