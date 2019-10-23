using System;
using System.Collections.Generic;
using System.Diagnostics;
using CGtk.Articulo;
using Gdk;

namespace CGtk {
    public partial class WCategoria : Gtk.Window {
        public WCategoria(Categoria categoria) :
                base(Gtk.WindowType.Toplevel) {
            Build();

            entryName.Text = categoria.Name ?? "";

            btExit.Clicked += (sender, e) => Destroy();

            btOk.Clicked += (sender, e) => {
                Categoria c = new Categoria(entryName.Text, categoria.Id);
                CategoryDAO.Save(c);
                Destroy();
            };
        }

        public string GetNombreCategoria() {
            return entryName.Text;
        }
    }
}
