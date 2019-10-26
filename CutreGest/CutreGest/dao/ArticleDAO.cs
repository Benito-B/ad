using System;
using System.Collections.Generic;
using System.Data;
using CSerpisAd;
using CutreGest.logic;
using CutreGest.model;

namespace CutreGest.dao {
    public static class ArticleDAO {

        private static string selectQuery = "select a.*, s.* from articles a, stocks s where a.id = s.articleId";

        public static ICollection<Article> GetAll(){
            ICollection<Article> articles = new List<Article>();
            IDbConnection conn = DbConn.Instance.Connection;

            IDbCommand dbCommand = conn.CreateCommand();
            dbCommand.CommandText = selectQuery;
            IDataReader dataReader = dbCommand.ExecuteReader();

            while (dataReader.Read())
                articles.Add(new Article((ulong)dataReader["id"], (string)dataReader["name"],
                    (ulong)dataReader["categoryid"], Decimal.ToDouble((decimal)dataReader["price"]), 
                    new Stock((ulong)dataReader["articleId"], (int)dataReader["currentStock"],
                    (int)dataReader["minStock"], (int)dataReader["maxStock"])));

            dataReader.Close();
            return articles;
        }

        private static string getQuery = "select a.*, s.* from articles a, stocks s where a.id = s.articleId " +
        	                            "and a.id = @id";

        public static Article GetArticle(ulong id){
            IDbCommand dbCommand = DbConn.Instance.Connection.CreateCommand();
            dbCommand.CommandText = getQuery;
            DbCommandHelper.AddParameter(dbCommand, "@id", id);
            IDataReader dataReader = dbCommand.ExecuteReader();
            dataReader.Read();
            dataReader.Close();
            return new Article((ulong)dataReader["id"], (string)dataReader["name"],
                    (ulong)dataReader["categoryid"], Decimal.ToDouble((decimal)dataReader["price"]),
                    new Stock((ulong)dataReader["articleId"], (int)dataReader["currentStock"],
                    (int)dataReader["minStock"], (int)dataReader["maxStock"]));
        }

        private static string deleteQuery = "delete from articles where id = @id";

        public static void Delete(Article a) {
            IDbCommand dbCommand = DbConn.Instance.Connection.CreateCommand();
            dbCommand.CommandText = deleteQuery;
            DbCommandHelper.AddParameter(dbCommand, "@id", a.Id);
            dbCommand.ExecuteNonQuery();
        }
        public static string GetArticleName(ulong id) {
            return GetArticle(id).Name;
        }

        public static void Save(Article a){
            if (a.Id == 0)
                insert(a);
            else
                update(a);
        }

        private static string insertQuery = "insert into articles(name, categoryid, price) values" +
        	" (@name, @categoryid, @price);select last_insert_id();";

        private static void insert(Article a){
            IDbConnection dbConn = DbConn.Instance.Connection;
            IDbCommand dbCommand = dbConn.CreateCommand();
            dbCommand.CommandText = insertQuery;
            DbCommandHelper.AddParameter(dbCommand, "@name", a.Name);
            DbCommandHelper.AddParameter(dbCommand, "@categoryid", a.Category);
            DbCommandHelper.AddParameter(dbCommand, "@price", a.Price);
            //Como estoy creando un artículo nuevo y no tiene id primero tengo que obtenerla
            ulong id = Convert.ToUInt64(dbCommand.ExecuteScalar());
            a.Stock.ArticleId = id;
            StockDAO.Insert(a.Stock);
        }

        private static string updateQuery = "update articles set name = @name, " +
        	"categoryid = @categoryid, price = @price where id = @id";

        private static void update(Article a){
            IDbConnection dbConn = DbConn.Instance.Connection;
            IDbCommand dbCommand = dbConn.CreateCommand();
            dbCommand.CommandText = updateQuery;
            DbCommandHelper.AddParameter(dbCommand, "@name", a.Name);
            DbCommandHelper.AddParameter(dbCommand, "@categoryid", a.Category);
            DbCommandHelper.AddParameter(dbCommand, "@price", a.Price);
            DbCommandHelper.AddParameter(dbCommand, "@id", a.Id);
            dbCommand.ExecuteNonQuery();
            StockDAO.Update(a.Stock);
        }
    }
}