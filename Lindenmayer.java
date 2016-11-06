import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Lindenmayer {
	private static String def = " def";

	// Le parametre String ... S sert a l'ensemble des regles, on lui passe
	// plusieurs strings.
	public static void lindenmayer(double D, double posX, double posY, double delta, double angle, int nIter,
			String... S) throws FileNotFoundException {

		PrintWriter out = new PrintWriter("Lindenmayer.eps");

		out.println("%!PS-Adobe-3.0 EPSF-3.0");
		out.println("%%Title: (dessin systeme L --- IFT2015 automne 2016)");
		out.println("%%Creator: (Youssef Zaki )");
		out.println("%%BoundingBox: 0 0 500 500");
		out.println("%%EndComments");
		out.println();
		out.println("%");
		out.println("% %%BeginProlog ... %%EndProlog sont des commentaires de structuration de document ");
		out.println("% pour denoter un bloc de definitions de nouveaux ensembles d'operateurs (procset) ");
		out.println("% et d'autres ressources (p.e., polices). ");
		out.println();
		out.println();
		out.println("%");
		out.println("% %%BeginResource...%%EndResource enferment une ressource ");
		out.println("% comme un ensemble d'operateurs (procset). ");
		out.println("%");
		out.println();
		out.println("%%BeginProlog");
		out.println("%%BeginResource: procset (graphisme tortue) 1.0 0");
		out.println();
		out.println("% --------- definition d'operations pour graphisme tortue");
		out.println("%");
		out.println("% Le systeme de coordonnes suit les deplacements et tours de la tortue,");
		out.println("% de maniere a ce que la tourtue est toujours a l'origine avec le next tourne ");
		out.println("% vers l'axe X. ");
		out.println("%");
		out.println("% Operations: ");
		out.println("% T:move avance la tortue ");
		out.println("% T:draw avance la tortue en dessinant une ligne entre les points de depart et arrivee");
		out.println("% T:turn tourne le nez de la tortue");
		out.println();
		out.println("/T:move % d T:move -\n" + "\t% avance la tortue par d (nombre --- positif, negatif ou 0)\n" + "{\n"
				+ "\t 0 translate % mettre l'origin a cette nouvelle position\n" + "} def");
		out.println();
		out.println("/T:draw % d T:draw -\n" + "\t% avance la torue par d en dessinant une ligne\n" + "{\n"
				+ "\t newpath \n" + "\t 0 0 moveto\n" + "\t 0 lineto\n" + "\t currentpoint \n" + "\t stroke \n"
				+ "\t translate \n" + "} def");
		out.println();
		out.print("/T:turn % angle T:turn -\n" + "\t % tourne le nez de la tourtue par l'angle (en degres)\n" + "{\n"
				+ "\t rotate\n" + "} def\n" + "\n" + "/T:init % x y angle T:init -\n"
				+ "\t % etat initiel de la tortue\n" + "{\t \n" + "\t 3 1 roll translate\n" + "\t rotate \n" + "} def");
		out.println();
		out.println();
		out.println("%%EndResource");
		out.println();
		out.println("%%BeginResource: procset (regle aleatoire) 1.0 0");
		out.println();
		out.println(
				"realtime srand % graine aleatoire --- pendant les tests, utiliser une valeur fixe (p.e., 2015 rand) si necessaire pour repetabilite");
		out.println();
		out.println("/L:rnd % [op1 op2 ...] L.rnd -\n" + "\t % choisit un operateur au hasard et l'execute\n"
				+ "\t % op1, op2 etc sont des noms (commencent par /)\n" + "{\n"
				+ "\t rand % nombre aleatoire entre 0 et 2^31-1\n" + "\t 1 index length % longueur du tableau \n"
				+ "\t mod % nombre aleatoire entre 0 et len-1\n" + "\t get \n" + "\t cvx % conversion a executable \n"
				+ "\t exec % executer\n" + "} def");
		out.println();
		out.println("%%EndResource");
		out.println();
		out.println("%");
		out.println("% La reste du fichier est genere par votre traducteur. Il n'est pas necessaire d'ecrire des ");
		out.println("% commentaires. ");
		out.println("% ---------------- BEGIN");
		out.println();
		out.println("%%BeginResource: procset (systeme L) 1.0 0");
		out.println();
		out.println("/L:d " + D + def + " % deplacement par defaut");
		out.println("/L:a " + delta + def + " % angle par defaut");
		out.println();
		out.println();
		/*
		 * on lit le tableau des regles de composition et on trait chaque cas
		 */
		// Les regles F -> ...
		for (int i = 1; i < S.length; i++) {
			// definir le nom de la variable
			out.println("/" + S[i].charAt(0) + i + " % iter F" + i + " -");
			// on garde la regle de composition
			String strComp = S[i].substring(2);
			out.println("{");
			// boucle pour lire les charactheres d'une chaine
			for (int k = 0; k < strComp.length(); k++) {
				if (strComp.charAt(k) == 'F' && k != strComp.length() - 1)
					out.println("\t dup " + strComp.charAt(k) + " % dupliquer iter pour les appels suivants a F ");
				if (strComp.charAt(k) == '[')
					out.println(
							"\t gsave % on encode '[' par l'operateur gsave qui sauvegarde l'etat courant du contexte graphique, incluant la tortue ");
				if (strComp.charAt(k) == '+')
					out.println("\t L:a T:turn % +");
				if (strComp.charAt(k) == '-')
					out.println("\t L:a neg T:turn % -");
				if (strComp.charAt(k) == ']')
					out.println(
							"\t grestore % on encode ']' par l'operateur grestore qui retablit le contexte graphique plus recemment sauvegarde par gsave");
				if (strComp.charAt(k) == 'F' && k == strComp.length() - 1)
					out.println("\t" + strComp.charAt(k));
				// si X ignorer
				if (strComp.charAt(k) == 'X') {
				}
				;
			}
			// fin de la procedure et de la definition du mot
			out.println("}" + def);
			out.println();
			// }
		}

		// Les regles f
		for (int i = 1; i < S.length; i++) {
			if (S[i].charAt(0) == 'f') {
				out.println("/" + S[i].charAt(0) + i);
				String strComp = S[i].substring(2);
				// debut corp de la procedure
				out.println("{");
				// boucle pour lire les charactheres d'une chaine
				for (int k = 0; k < strComp.length(); k++) {
					if (strComp.charAt(k) == 'f' && k != strComp.length() - 1)
						out.println("\t dup " + strComp.charAt(k));
					if (strComp.charAt(k) == '[')
						out.println("\t gsave");
					if (strComp.charAt(k) == '+')
						out.println("\t L:a T:turn");
					if (strComp.charAt(k) == '-')
						out.println("\t L:a neg T:turn");
					if (strComp.charAt(k) == ']')
						out.println("\t grestore");
					if (strComp.charAt(k) == 'f' && k == strComp.length() - 1)
						out.println("\t" + strComp.charAt(k));
					// si X ignorer
					if (strComp.charAt(k) == 'X') {
					}
					;
				}
				// fin de la procedure et de la definition du mot
				out.println("}" + def);
				out.println();
			}
		}

		/*
		 * traitement de la chaine de depart F F peut etre n'importe quelle
		 * combinaison de {+, -, F}
		 */
		String str = S[0];
		// definir le nom de la variable
		out.println("/F");
		// debut corp de la procedure
		out.println("{");
		out.println("\t dup");
		out.println("\t 0 eq");
		out.println("\t{ L:d T:draw pop }");
		out.println("\t{ 1 sub");
		// costruir la liste des regles des composition pour F
		String listeF = "";
		for (int j = 1; j < S.length && S[j].charAt(0) == 'F'; j++) {
			if (j < S.length - 1)
				listeF = listeF + "/F" + j + " ";
			else
				listeF = listeF + "/F" + j;
		}
		out.println("\t\t" + "[" + listeF + "]" + " L:rnd");
		out.println("\t} ifelse");
		out.println();

		// fin de la procedure
		out.println("}" + def);
		out.println();
		// traitement de la chaine de composition f
		// definir le nom de la variable
		out.println("/f");
		out.println("{");
		out.println("\t dup");
		out.println("\t 0 eq");
		out.println("\t{ L:d T:move pop }");
		out.println("\t{ 1 sub");
		// costruir la liste des regles des pour f
		String liste = "";
		for (int n = 1; n < S.length; n++) { // && r[n].charAt(0) == 'f'
			if (S[n].charAt(0) == 'f')
				liste = liste + "/f" + n + " ";
		}
		out.println("\t\t" + "[" + liste + "]" + " L:rnd");
		out.println("\t} ifelse");
		out.println();
		// fin de la procedure
		out.println("}" + def);
		out.println();
		// definition de la chaine omega
		out.println("/omega {");
		if (str.length() > 1) {
			for (int i = 0; i < str.length(); i++) {
				if (str.charAt(i) == 'F') {
					out.println("\t dup F");
				}
				if (str.charAt(i) == '+')
					out.println("\t L:a T:turn");
				if (str.charAt(i) == '-')
					out.println("\t L:a neg T:turn");
			}
			out.println("\t pop");
		} else {
			out.print("\t F");
		}
		out.println("\t }" + def);
		out.println();
		out.println("%%EndResource");
		out.println("%%EndProlog");
		out.println();
		out.println("% ------- fin de definitions, dessin commence ici");
		out.println();
		out.println(posX + " " + posY + " % x0 y0");
		out.println(angle + " " + "% angle initial");
		out.println("T:init");
		out.println();
		out.println(nIter + " " + " % nombre iterations");
		out.println("omega % symbole de debut");
		out.println();
		out.println("% le fichier doit finir par le commentaire special %%EOF");
		out.println("%%EOF");
		out.close();
	}

	public static void main(String[] args) throws FileNotFoundException {

		lindenmayer(5, 250, 200, 60, 60, 3, "F--F--F", "F:F+F--F+F");
	}
}