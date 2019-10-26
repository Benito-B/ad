using System;
using Gtk;

namespace CutreGest.view.windows {
    public class DialogHelper {
        public DialogHelper() {
        }

        public static void ShowDialog(string title, string body, Window parent, DialogFlags flags, params object[] buttons) {
            Dialog dialog = null;
            try {
                dialog = new Dialog(title, parent, flags, buttons);
                dialog.VBox.Add(new Label(body));
                dialog.ShowAll();
                dialog.Run();
            } finally {//Por algún motivo los Dialog no se destruyen al salir...
                if (dialog != null)
                    dialog.Destroy();
            }
        }
    }
}
