using System;
using Gtk;
using CutreGest.dao;
using CutreGest.model;
using CutreGest.view.windows;


public partial class MainWindow : Gtk.Window {

    public MainWindow() : base(Gtk.WindowType.Toplevel) {
        Build();

        SetPosition(WindowPosition.CenterAlways);
        entryUserName.Activated += (sender, e) => btLogin.Click();
        entryUserPass.Activated += (sender, e) => btLogin.Click();
        btExit.Clicked += (sender, e) => Application.Quit();
        btLogin.Clicked += (sender, e) => {
            if (!fieldsAreEmpty()) {
                User user = UserDAO.Login(new LoginUser(entryUserName.Text, entryUserPass.Text));
                if (user == null)//Login devuelve null si los campos no son correctos
                    DialogHelper.ShowDialog("Login incorrecto", "El usuario y la contraseña no coinciden.", this,
                        DialogFlags.DestroyWithParent | DialogFlags.Modal, "Aceptar", ResponseType.Accept);
                else {
                    ListArticleWindow listArticleWindow = new ListArticleWindow(user);
                    listArticleWindow.Show();
                    Destroy();
                }
            }
        };
    }

    protected void OnDeleteEvent(object sender, DeleteEventArgs a) {
        Application.Quit();
        a.RetVal = true;
    }

    /**
     * Si los campos están vacíos devuelve false y además muestra un pequeño dialog informando al usuario
     */
    private bool fieldsAreEmpty() {
        if (entryUserName.Text.Trim().Equals("") || entryUserPass.Text.Trim().Equals("")) {
            DialogHelper.ShowDialog("Error", "Los campos no pueden estar vacíos", this,
                DialogFlags.DestroyWithParent | DialogFlags.Modal, "Aceptar", ResponseType.Accept);
            return true;
        }
        return false;
    }
}

//Mostrar un diálogo y usar la salida para hacer algo
/*Dialog dialog = null;
		ResponseType response = ResponseType.None;

		try {
			dialog = new Dialog (
				"Dialog Title",
				this,
				DialogFlags.DestroyWithParent | DialogFlags.Modal,
				"Go ahead bro", ResponseType.Yes,
				"Cancel", ResponseType.No
			);
			dialog.VBox.Add (new Label ("Dialog contents"));
			dialog.ShowAll ();

			response = (ResponseType) dialog.Run ();
		} finally {
			if (dialog != null)
				dialog.Destroy ();
		}
		if (response == ResponseType.No)
			Console.WriteLine ("NO NO");
		else
			Console.WriteLine("Yes yes");*/