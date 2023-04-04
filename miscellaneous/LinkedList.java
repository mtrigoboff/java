public class LinkedList {

	static class Node {
		String data;
		Node next;

		Node(String data, Node next) {
			this.data = data;
			this.next = next;
		}
	}
	
	Node first = null;

	void add(String data) {
		first = new Node(data, first);
	}

	public static void main(String[] args) {
		LinkedList lst = new LinkedList();
		lst.add("C");
		lst.add("B");
		lst.add("A");

		Node n = lst.first;
		while (n != null) {
			System.out.println(n.data);
			n = n.next;
		}
	}
}
