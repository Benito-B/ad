using System;
using System.Security.Cryptography;
using System.Text;
using System.Linq;
namespace CSerpisAd{
	public class Hasher
	{

		public Hasher ()
		{
		}

		private static string generateString (HashAlgorithm hashAlgorithm, string s)
		{
			//Digiero la cadena y obtengo los bytes del resultado
			byte [] bytes = Encoding.UTF8.GetBytes (s);
			byte [] result = hashAlgorithm.ComputeHash (bytes);
			//Devuelvo el resultado como una cadena de texto en hexadecimal
			return string.Join (string.Empty, result.Select ((arg) => arg.ToString ("x2")));
		}

		public static string MD5 (string str)
		{
			return generateString (new MD5CryptoServiceProvider (), str);
		}


		public static string SHA256 (string str)
		{
			return generateString (new SHA256Managed (), str);
		}

	}
}
