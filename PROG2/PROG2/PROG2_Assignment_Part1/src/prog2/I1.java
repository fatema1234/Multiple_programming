package prog2;


	import java.util.NoSuchElementException;

	/**
	 * Ett v�ldigt enkelt testprogram som testar n�gra av
	 * metoderna i Graph och kastar undantag om de inte fungerar.
	 * <p>
	 * Absolut inte helt�ckande tester s�:
	 * fel == inte ok.
	 * inga fel != godk�nt (det kan finnas andra fel).
	 * <p>
	 * Filen ska inte l�mnas in.
	 */
	public class I1 {

		private static final double VERSION = 1.1;
		private static final String UPDATED = "2021-04-15";

		private static final String MISSING_NODE = "Z";
		private final Graph<String> stringGraph = new ListGraph<>();

		public I1() {
			System.out.println("A small test program that tries to check to fundamental operations.");
			System.out.printf("Version %.1f, last updated %s%n", VERSION, UPDATED);
			System.out.println("No errors doesn't mean the code is correct, just that it passed very basic testing.");
		}

		public static void main(String[] args) throws Exception {

			var app = new I1();

			// testa att grafen �r tom fr�n start
			app.test0_new_graph_is_empty();

			// testa att l�gga till nod
			app.test1_add_no_duplicate();

			// testa att l�gga till samma nod igen
			app.test2_add_with_duplicate();

			// testa connect
			app.test3_connect();

			// testa disconnect
			app.test4_disconnect();

			// testa remove
			app.test5_remove();

			// klart
			app.tests_finished();
		}

		private void test0_new_graph_is_empty() throws Exception {
			if (!stringGraph.getNodes().isEmpty())
				throw new Exception("Error: newly created graph should be empty.");
		}

		private void test1_add_no_duplicate() throws Exception {
			stringGraph.add("A");
			if (stringGraph.getNodes().size() != 1)
				throw new Exception("Error: graph should have 1 node after add 'A'.");
		}

		private void test2_add_with_duplicate() throws Exception {
			stringGraph.add("A");
			if (stringGraph.getNodes().size() != 1)
				throw new Exception("Error: graph should still have 1 node after add 'A' a second time.");
		}

		private void test3_connect() throws Exception {
			test3_connect_ok();
			test3_connect_throws_connection_exists();
			test3_connect_throws_missing_node();
			test3_connect_throws_negative_weight();
		}

		private void test3_connect_ok() throws Exception {
			stringGraph.add("B");
			if (stringGraph.getNodes().size() != 2)
				throw new Exception("Error: graph should have 2 nodes after adding 'A' and 'B'");

			stringGraph.connect("A", "B", "A <=> B", 1);

			if (stringGraph.getEdgeBetween("A", "B") == null)
				throw new Exception("Error: no edge exist between 'A' and 'B'. (Or getEdgeBetween doesn't work...");
			if (stringGraph.getEdgeBetween("B", "A") == null)
				throw new Exception("Error: no edge exist between 'B' and 'A'. (Or getEdgeBetween doesn't work...");
		}

		private void test3_connect_throws_connection_exists() throws Exception {
			stringGraph.add("B");
			if (stringGraph.getNodes().size() != 2)
				throw new Exception("Error: graph should have 2 node after adding 'A' and 'B'.");

			try {
				stringGraph.connect("A", "B", "A <=> B", 1);
			} catch (IllegalStateException e) {
				// ok
				return;
			}
			throw new Exception("Error: should have thrown IllegalStateException.");
		}

		private void test3_connect_throws_missing_node() throws Exception {
			stringGraph.add("B");
			if (stringGraph.getNodes().size() != 2)
				throw new Exception("Error: graph should have 2 node after adding 'A' and 'B'.");

			try {
				stringGraph.connect("A", MISSING_NODE, "A <=> Z", 1);
			} catch (NoSuchElementException e) {
				// ok
				return;
			}
			throw new Exception("Error: should have thrown NoSuchElementException.");
		}

		private void test3_connect_throws_negative_weight() throws Exception {
			stringGraph.add("B");
			if (stringGraph.getNodes().size() != 2)
				throw new Exception("Error: graph should have 2 node after adding 'A' and 'B'.");

			try {
				stringGraph.connect("A", "B", "A <=> B", -1);
			} catch (IllegalArgumentException e) {
				// ok
				return;
			}
			throw new Exception("Error: should have thrown IllegalArgumentException.");
		}

		private void test4_disconnect() throws Exception {
			test4_disconnect_ok();
			test4_disconnect_throws_no_connection_exists();
			test4_disconnect_throws_missing_node();
		}

		private void test4_disconnect_ok() throws Exception {
			stringGraph.disconnect("A", "B");
			if (stringGraph.getEdgeBetween("A", "B") != null) {
				throw new Exception("Error: edge not disconnected.");
			}
		}

		private void test4_disconnect_throws_missing_node() throws Exception {
			try {
				stringGraph.disconnect("A", MISSING_NODE);
			} catch (NoSuchElementException e) {
				// ok
				return;
			}
			throw new Exception("Error: should have thrown NoSuchElementException.");
		}

		private void test4_disconnect_throws_no_connection_exists() throws Exception {
			stringGraph.add("C");
			try {
				stringGraph.disconnect("A", "C");
			} catch (IllegalStateException e) {
				// ok
				return;
			}
			throw new Exception("Error: should have thrown IllegalStateException.");
		}

		private void test5_remove() throws Exception {
			test5_remove_missing_node();
			test5_remove_ok();
		}

		private void test5_remove_missing_node() throws Exception {
			try {
				stringGraph.remove(MISSING_NODE);
			} catch (NoSuchElementException e) {
				// ok
				return;
			}
			throw new Exception("Error: should have thrown NoSuchElementException.");
		}

		private void test5_remove_ok() throws Exception {
			stringGraph.remove("C");
			if (stringGraph.getNodes().contains("C")) {
				throw new Exception("Error: node not removed.");
			}
		}

		private void tests_finished() {
			System.out.printf("%n ==> No exceptions thrown so all tests passed. <== %n");
		}
	}


