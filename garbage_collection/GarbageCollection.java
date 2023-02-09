
import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.WeakHashMap;

/* demonstrates resurrection of an instance of Key
 * 
 * Java garbage collection is described in chapter 12 of:
 * "The Java Programming Language"
 *   by Ken Arnold, James Gosling, and David Holmes
 *   published by Addison-Wesley
 *   
 * finalization:
 *   the finalize() method
 *     may not be called directly
 *     MUST call super.finalize() after doing your own finalizing
 *       should do this within a finally clause, so that
 *         the call to super.finalize() happens whether
 *         or not an exception is thrown by the code
 *         in your overriding finalize() method
 *       but don't call super.finalize()
 *         if you're going to resurrect
 *   resurrection
 *     can only happen ONCE for an object
 * 
 * reachability:
 *   strongly          normal reference
 *   softly            MAY be reclaimed at discretion of gc
 *   weakly            WILL be reclaimed by gc
 *   finalizer         ready to be finalized
 *   phantom           has been finalized
 * 
 * java.lang.ref:
 *   SoftReference     MAY be garbage collected
 *   WeakReference     WILL be garbage collected
 *   PhantomReference  HAS been garbage collected
 *   ReferenceQueue    how to detect changes in reachability
 */
class GarbageCollection {

	private static String divider = "-------------------------%n%n";
	private static Key resurrectedKey;

	private static class Key {

		private String string;
		private boolean resurrect;

		Key(String string, boolean resurrect) {
			this.string = string;
			this.resurrect = resurrect;
		}

		public String toString() {
			return string;
		}

		// resurrect key
		public void finalize() {
			if (resurrect) {
				System.out.printf("Key.finalize called to resurrect \"%s\"%n%n", this);
				resurrectedKey = this;
			} else {
				System.out.printf("Key.finalize called for \"%s\"%n%n", this);
				try {
					super.finalize();
				} catch (Throwable t) {
					System.out.printf("Key.finalize threw %s%n%n", t.getMessage());
				}
			}
		}
	}

	// print status of weak and phantom references
	@SuppressWarnings("unchecked")
	private static void printRefStatus(ReferenceQueue<Key> weakRQ, ReferenceQueue<Key> phantomRQ) {
		WeakReference<Key> weakRef = (WeakReference<Key>) weakRQ.poll();
		PhantomReference<Key> phantomRef = (PhantomReference<Key>) phantomRQ.poll();

		System.out.printf("weak ref queue poll:%n  %s%n", weakRef);
		if (weakRef != null) {
			System.out.println("    polled object: " + weakRef.get());
		}
		System.out.printf("%nphantom ref queue poll:%n  %s%n%n", phantomRef);
	}

	private static void referenceDemo(boolean resurrect) {
		System.out.printf("Reference Demo %s%n%n", resurrect ? "(resurrectable)" : "(not resurrectable)");

		resurrectedKey = null;

		Key key = new Key("key", resurrect);

		ReferenceQueue<Key> weakRQ = new ReferenceQueue<>();
		WeakReference<Key> weakRef = new WeakReference<>(key, weakRQ);

		ReferenceQueue<Key> phantomRQ = new ReferenceQueue<>();
		PhantomReference<Key> phantomRef = new PhantomReference<>(key, phantomRQ);

		System.out.printf("weak reference for \"key\":%n  %s%n%n", weakRef);
		System.out.printf("phantom reference for \"key\":%n  %s%n%n", phantomRef);

		System.out.printf("eliminating the only strong reference to \"%s\"%n%n", key);
		key = null;

		printRefStatus(weakRQ, phantomRQ);

		// request a garbage collection
		System.out.printf("garbage collector called%n%n");
		System.gc();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}

		printRefStatus(weakRQ, phantomRQ);

		System.out.printf("resurrected Key = \"%s\"%n%n", resurrectedKey);

		System.out.printf(divider);
	}

	private static void weakHashMapDemo(boolean doGC) {
		System.out.printf("WeakHashMap Demo %s%n%n", doGC ? "(will request garbage collection)"
				: "(will not request garbage collection)");

		Key k1 = new Key("key1", false);
		Key k2 = new Key("key2", false);

		WeakHashMap<Key, String> whm = new WeakHashMap<>();

		// add two pairs to weak hash map
		whm.put(k1, "value1");
		whm.put(k2, "value2");

		System.out.println("contents of WeakHashMap whm: " + whm);

		// eliminate first key
		System.out.printf("%neliminating the only strong reference to \"key1\"%n%n");
		k1 = null;

		if (doGC) {
			// request a garbage collection
			System.out.printf("garbage collection requested%n%n");
			System.gc();
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
			}
		}

		System.out.println("contents of WeakHashMap whm: " + whm);

		System.out.printf("%n%s%n%n", divider.substring(0, divider.length() - 4));
	}

	public static void main(String[] args) {
		System.out.printf("Garbage Collection%n%n");
		System.out.printf(divider);

		referenceDemo(false);
		referenceDemo(true);

		weakHashMapDemo(true);
		weakHashMapDemo(false);
	}
}
