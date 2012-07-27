package gdw.utils;

public class LinearIstGut
{
	public static void print(String c, double[][] m)
	{
		for (int i = 0; i < m.length; i++)
		{
			System.out.print(c + "[ " + i + "][*]: ");
			for (int j = 0; j < m[i].length; j++)
			{
				System.out.print(m[i][j] + ", ");
			}
			System.out.println(" ");
		}
	}

	public static void print(String c, double[] m)
	{
		for (int i = 0; i < m.length; i++)
		{
			System.out.println(c + "[ " + i + "]: " + m[i] + ", ");
		}
	}

	public static double[][] cloneMatrix(double[][] matrix)
	{
		int m = matrix.length;
		int n = matrix[0].length;
		double[][] clone = new double[m][n];
		for (int i = 0; i < m; i++)
		{
			for (int j = 0; j < n; j++)
			{
				clone[i][j] = matrix[i][j];
			}
		}
		return clone;
	}

	public static double[] produkt(double s, double[] vector)
	{
		int n = vector.length;
		double[] newVec = new double[n];
		for (int i = 0; i < n; ++i)
		{
			newVec[i] = s * vector[i];
		}
		return newVec;
	}

	public static double[][] produkt(double s, double[][] matrix)
	{
		int n = matrix.length;
		int m = matrix[0].length;
		double[][] newMatrix = new double[n][m];
		for (int i = 0; i < n; ++i)
		{
			for (int j = 0; j < m; ++j)
			{
				newMatrix[i][j] = s * matrix[i][j];
			}
		}
		return newMatrix;
	}

	public static double[][] matMult(double[][] matrix1, double[][] matrix2)
	{
		// Dimensionen und Schleifengrenzen
		int l = matrix1.length; // Anzahl Zeilen Matrix1
		int m = matrix2.length; // Anzahl Zeilen Matris2
		int n = matrix2[0].length; // Anzahl Spalten Matrix2
		// System.out.println(l + " " + m + " "+ n);

		if (m != matrix1[0].length)
			// throw new RuntimeException();
			throw new IndexOutOfBoundsException(
					"Array bounds incompatible: matrix1 [" + l + "]["
							+ matrix1[0].length + "] * matrix2[" + m + "][" + n
							+ "]");
		else
		{
			double[][] matrix3 = new double[l][n];
			for (int i = 0; i < n; i++)
			{ // f�r alle Spalten von matrix2 und matrix3
				for (int j = 0; j < l; j++)
				{ // f�r alle Zeilen von matrix1 und matrix3
					matrix3[j][i] = (double) 0.0;
					for (int k = 0; k < m; k++)
					{ // summiere f�r alle Spalten von matrix1 (Zeilen von
						// Matrix2)
						matrix3[j][i] += +matrix1[j][k] * matrix2[k][i];
					}
				}
			}
			return matrix3;
		}
	}// MatMult

	public static double[] matMult(double[][] matrix1, double[] vektor)
	{
		// Dimensionen und Schleifengrenzen
		int l = matrix1.length; // Anzahl Zeilen Matrix1
		int m = vektor.length; // Anzahl Zeilen Matris2
		// System.out.println(l + " " + m + " "+ n);

		if (m != matrix1[0].length)
			// throw new RuntimeException();
			throw new IndexOutOfBoundsException(
					"Array bounds incompatible: matrix1 [" + l + "]["
							+ matrix1[0].length + "] * matrix2[" + m + "]");
		else
		{
			double[] matrix3 = new double[l];
			for (int j = 0; j < l; j++)
			{ // f�r alle Zeilen von matrix1 und matrix3
				matrix3[j] = (double) 0.0;
				for (int k = 0; k < m; k++)
				{ // summiere f�r alle Spalten von matrix1 (Zeilen von Matrix2)
					matrix3[j] += +matrix1[j][k] * vektor[k];
				}
			}
			return matrix3;
		}
	}// matMult

	public static double[][] invertiereMatrix(double[][] m)
	{
		int n = m.length;
		double[][] tmp = new double[n][2 * n];
		// print("Parameter", m);
		// --- Kopie der Matrix mit Einheitsmatrix rechts
		for (int i = 0; i < n; i++)
		{
			for (int j = 0; j < n; j++)
			{
				tmp[i][j] = m[i][j];
			}
			tmp[i][i + n] = 1d;
		}
		// --- Dreieckselimination
		double[][] tmp1 = LinearIstGut.dreiecksElimination(tmp);
		// --- Rueckwaertssubstitution
		return LinearIstGut.rueckwaertsSubstitutionMehrereGLS(tmp1);

	}

	public static double[] gauss(double[][] m)
	{
		return rueckwaertsSubstitution(dreiecksElimination(m));
	}

	public static double[][] gaussMehrereRechteSeiten(double[][] m)
	{
		return rueckwaertsSubstitutionMehrereGLS(dreiecksElimination(m));
	}

	public static double[] rueckwaertsSubstitution(double[][] m)
	{
		int n = m.length;
		double[] x = new double[n];
		x[n - 1] = m[n - 1][n] / m[n - 1][n - 1];
		for (int i = n - 2; i > -1; i--)
		{
			double tmp = m[i][n];
			for (int j = i + 1; j < n; j++)
			{
				tmp -= m[i][j] * x[j];
			}
			x[i] = tmp / m[i][i];
		}
		return x;
	}

	public static double[][] rueckwaertsSubstitutionMehrereGLS(double[][] m)
	{
		int n = m.length;
		int nGls = m[0].length - n;
		double[][] x = new double[n][nGls];
		for (int k = 0; k < nGls; k++)
		{
			x[n - 1][k] = m[n - 1][n + k] / m[n - 1][n - 1];
			for (int i = n - 2; i > -1; i--)
			{
				double tmp = m[i][n + k];
				for (int j = i + 1; j < n; j++)
				{
					tmp -= m[i][j] * x[j][k];
				}
				x[i][k] = tmp / m[i][i];
			}
		}
		return x;
	}

	public static double[][] dreiecksElimination(double[][] m)
	{
		double[][] r = LinearIstGut.cloneMatrix(m);
		int n = r.length;
		// --- fuer alle zu eliminierenden Spalten
		for (int j = 0; j < n - 1; j++)
		{
			double max = Math.abs(r[j][j]);
			int pivotZeile = j;
			// ---- Pivotsuche
			for (int i = j + 1; i < n; i++)
			{
				if (Math.abs(r[i][j]) > max)
				{
					max = Math.abs(r[i][j]);
					pivotZeile = i;
				}
			}
			// ---- Zeilentausch
			if (pivotZeile != j)
			{
				for (int k = j; k < r[j].length; k++)
				{
					double tmp = r[j][k];
					r[j][k] = r[pivotZeile][k];
					r[pivotZeile][k] = tmp;
				}
			}
			// ---- Elimination
			for (int i = j + 1; i < n; i++)
			{
				double faktor = r[i][j] / r[j][j];
				for (int k = j; k < r[j].length; k++)
				{
					r[i][k] -= faktor * r[j][k];
				}
			}
		}
		return r;
	}

	public static double determinante(double[][] m)
	{
		double[][] tmp = dreiecksElimination(m);
		// --- Produkt der Hauptdiagonalelemente
		double r = tmp[0][0];
		for (int i = 1; i < tmp.length; i++)
		{
			r *= tmp[i][i];
		}
		return r;
	}

	public static double hadamardKondition(double[][] m)
	{
		double r = Math.abs(determinante(m));
		int n = m.length;
		for (int i = 0; i < n; i++)
		{
			double alpha = 0d;
			for (int j = 0; j < n; j++)
			{
				alpha += m[i][j] * m[i][j];
			}
			r /= Math.sqrt(alpha);
		}
		return r;
	}
	
	
	
	/////////////////////////////////////////////////
	// Addon
	
	public static float[] calculateIntersectionPoint(float[] u1, float[] v1, float[] u2, float[] v2)
	{
		float s1 = 0;
		float s2 = 0;
		
		double[][] solveMatrix = new double[][] {{v1[0], -v2[0]}, {v1[1], -v2[1]}};
		solveMatrix = LinearIstGut.invertiereMatrix(solveMatrix);
		
		double[] result = {u2[0] - u1[0], u2[1] - u1[1]};
		result = LinearIstGut.matMult(solveMatrix, result);
		
		s1 = (float) result[0];
		s2 = (float) result[1];
		
		if (s1 < 0 || s2 < 0) return new float[] {0, 0};
		if (s1 > 1 || s2 > 1) return new float[] {0, 0};
		
		result[0] = u1[0] + s1 * v1[0];
		result[1] = u1[1] + s1 * v1[1];
		
		return new float[] {(float) result[0], (float) result[1]};
	}
}
