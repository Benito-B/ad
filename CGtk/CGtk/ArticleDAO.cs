using System;
using System.Collections.Generic;
using System.Data;

namespace CGtk {
    public class ArticleDAO {
        public ArticleDAO() {
        }
        /*
         * Articulo(id(int),nombre(string),categoria(int),precio(float))
         */

        public static ICollection<Article> GetAll() {
            ICollection<Article> articles = new List<Article>();
            IDbConnection dbConn = DbConn.GetInstance().GetConnection();
            IDbCommand dbCommand = dbConn.CreateCommand();
            dbCommand.CommandText = "select a.id, a.nombre, a.categoria, a.precio, " +
            	"c.nombre as 'nombreCategoria' from articulo a, categoria c where a.categoria = c.id";
            IDataReader dataReader = dbCommand.ExecuteReader();
            while (dataReader.Read()) {
                articles.Add(new Article((ulong)dataReader.GetInt64(0),
                    dataReader.GetString(1),
                    new Categoria(dataReader.GetString(4), (ulong)dataReader.GetInt64(0)),
                    dataReader.GetFloat(3)));
            }
            dataReader.Close();
            return articles;
        }
    }
}
