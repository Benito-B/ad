using System;
using System.Collections.Generic;

namespace CGtk {
    public partial class WArticle : Gtk.Window {
        public WArticle() :
                base(Gtk.WindowType.Toplevel) {
            this.Build();

            Dictionary<string, ulong> dict = new Dictionary<string, ulong>();

            foreach (Categoria c in CategoryDAO.GetAll()) {
                dict.Add(c.Name, c.Id);
                cbCategory.AppendText(c.Name);
            }
                
        }
    }
}
