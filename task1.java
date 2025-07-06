package module2.task1;

import java.util.*;
import java.util.function.Consumer;

public class task1 {
    public static void main(String[] args) {
        MyHashSet<String> myHs = new MyHashSet<>();
        myHs.add("Rogue");
        myHs.add("Fighter");
        myHs.add("Wizard");
        myHs.add("Rogue");
        myHs.remove("Fighter");
        System.out.println(myHs);

        Set hs = new HashSet<>();
        hs.add("Rogue");
        hs.add("Fighter");
        hs.add("Wizard");
        hs.add("Rogue");
        hs.remove("Fighter");
        System.out.println(hs);


        MyLinkedList<String> myLinkedList = new MyLinkedList<>();
        myLinkedList.add("Rogue");
        myLinkedList.add("Fighter");
        myLinkedList.add("Wizard");
        myLinkedList.add("Barbarian");
        myLinkedList.add(0,"Rogue");
        myLinkedList.remove(2);
        System.out.println(myLinkedList);
        System.out.println(myLinkedList.get(3));
        myLinkedList.addAll(Arrays.asList("Monk", "Sorcerer"));
        System.out.println(myLinkedList);

        List<String> linkedList = new LinkedList<>();
        linkedList.add("Rogue");
        linkedList.add("Fighter");
        linkedList.add("Wizard");
        linkedList.add("Barbarian");
        linkedList.add(0,"Rogue");
        linkedList.remove(2);
        System.out.println(linkedList);

        myLinkedList.addAll(linkedList);
        System.out.println(myLinkedList);

    }

    public static class MyLinkedList<E> {
        transient int size = 0;
        transient int modCount = 0;
        transient MyLinkedList.Node<E> first;
        transient MyLinkedList.Node<E> last;

        public boolean add(E e) {
            linkLast(e);
            return true;
        }

        void linkLast(E e) {
            final Node<E> l = last;
            final Node<E> newNode = new Node<>(l, e, null);
            last = newNode;
            if (l == null)
                first = newNode;
            else
                l.next = newNode;
            size++;
            modCount++;
        }

        public boolean addAll(Collection<? extends E> c) {
            return addAll(size, c);
        }

        public boolean addAll(int index, Collection<? extends E> c) {
            checkPositionIndex(index);

            Object[] a = c.toArray();
            int numNew = a.length;
            if (numNew == 0)
                return false;

            Node<E> pred, succ;
            if (index == size) {
                succ = null;
                pred = last;
            } else {
                succ = node(index);
                pred = succ.prev;
            }

            for (Object o : a) {
                E e = (E) o;
                Node<E> newNode = new Node<>(pred, e, null);
                if (pred == null)
                    first = newNode;
                else
                    pred.next = newNode;
                pred = newNode;
            }

            if (succ == null) {
                last = pred;
            } else {
                pred.next = succ;
                succ.prev = pred;
            }

            size += numNew;
            modCount++;
            return true;
        }

        public E get(int index) {
            checkElementIndex(index);
            return node(index).item;
        }

        public void add(int index, E element) {
            checkPositionIndex(index);

            if (index == size)
                linkLast(element);
            else
                linkBefore(element, node(index));
        }

        void linkBefore(E e, Node<E> succ) {
            final Node<E> pred = succ.prev;
            final Node<E> newNode = new Node<>(pred, e, succ);
            succ.prev = newNode;
            if (pred == null)
                first = newNode;
            else
                pred.next = newNode;
            size++;
            modCount++;
        }

        Node<E> node(int index) {
            if (index < (size >> 1)) {
                Node<E> x = first;
                for (int i = 0; i < index; i++)
                    x = x.next;
                return x;
            } else {
                Node<E> x = last;
                for (int i = size - 1; i > index; i--)
                    x = x.prev;
                return x;
            }
        }

        private void checkPositionIndex(int index) {
            if (!isPositionIndex(index))
                throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
        }

        private boolean isPositionIndex(int index) {
            return index >= 0 && index <= size;
        }

        private String outOfBoundsMsg(int index) {
            return "Index: "+index+", Size: "+size;
        }

        public boolean remove(Object o) {
            if (o == null) {
                for (Node<E> x = first; x != null; x = x.next) {
                    if (x.item == null) {
                        unlink(x);
                        return true;
                    }
                }
            } else {
                for (Node<E> x = first; x != null; x = x.next) {
                    if (o.equals(x.item)) {
                        unlink(x);
                        return true;
                    }
                }
            }
            return false;
        }

        public E remove(int index) {
            checkElementIndex(index);
            return unlink(node(index));
        }

        E unlink(Node<E> x) {
            final E element = x.item;
            final Node<E> next = x.next;
            final Node<E> prev = x.prev;

            if (prev == null) {
                first = next;
            } else {
                prev.next = next;
                x.prev = null;
            }

            if (next == null) {
                last = prev;
            } else {
                next.prev = prev;
                x.next = null;
            }

            x.item = null;
            size--;
            modCount++;
            return element;
        }

        private void checkElementIndex(int index) {
            if (!isElementIndex(index))
                throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
        }

        private boolean isElementIndex(int index) {
            return index >= 0 && index < size;
        }

        public ListIterator<E> listIterator(int index) {
            checkPositionIndex(index);
            return new MyLinkedList.ListItr(index);
        }

        private class ListItr implements ListIterator<E> {
            private Node<E> lastReturned;
            private Node<E> next;
            private int nextIndex;
            private int expectedModCount = modCount;

            ListItr(int index) {
                next = (index == size) ? null : node(index);
                nextIndex = index;
            }

            public boolean hasNext() {
                return nextIndex < size;
            }

            public E next() {
                checkForComodification();
                if (!hasNext())
                    throw new NoSuchElementException();

                lastReturned = next;
                next = next.next;
                nextIndex++;
                return lastReturned.item;
            }

            public boolean hasPrevious() {
                return nextIndex > 0;
            }

            public E previous() {
                checkForComodification();
                if (!hasPrevious())
                    throw new NoSuchElementException();

                lastReturned = next = (next == null) ? last : next.prev;
                nextIndex--;
                return lastReturned.item;
            }

            public int nextIndex() {
                return nextIndex;
            }

            public int previousIndex() {
                return nextIndex - 1;
            }

            public void remove() {
                checkForComodification();
                if (lastReturned == null)
                    throw new IllegalStateException();

                Node<E> lastNext = lastReturned.next;
                unlink(lastReturned);
                if (next == lastReturned)
                    next = lastNext;
                else
                    nextIndex--;
                lastReturned = null;
                expectedModCount++;
            }

            public void set(E e) {
                if (lastReturned == null)
                    throw new IllegalStateException();
                checkForComodification();
                lastReturned.item = e;
            }

            public void add(E e) {
                checkForComodification();
                lastReturned = null;
                if (next == null)
                    linkLast(e);
                else
                    linkBefore(e, next);
                nextIndex++;
                expectedModCount++;
            }

            public void forEachRemaining(Consumer<? super E> action) {
                Objects.requireNonNull(action);
                while (modCount == expectedModCount && nextIndex < size) {
                    action.accept(next.item);
                    lastReturned = next;
                    next = next.next;
                    nextIndex++;
                }
                checkForComodification();
            }

            final void checkForComodification() {
                if (modCount != expectedModCount)
                    throw new ConcurrentModificationException();
            }
        }

        public String toString() {
            Iterator<E> it = listIterator(0);
            if (! it.hasNext())
                return "[]";

            StringBuilder sb = new StringBuilder();
            sb.append('[');
            for (;;) {
                E e = it.next();
                sb.append(e == this ? "(this Collection)" : e);
                if (! it.hasNext())
                    return sb.append(']').toString();
                sb.append(',').append(' ');
            }
        }

        private static class Node<E> {

            E item;
            Node<E> next;
            Node<E> prev;

            Node(Node<E> prev, E element, Node<E> next) {
                this.item = element;
                this.next = next;
                this.prev = prev;
            }
        }
    }




    public static class MyHashSet<E> {

        transient HashMap<E,Object> map;
        static final Object PRESENT = new Object();

        public MyHashSet() {
            map = new HashMap<>();
        }

        public boolean add(E e) {
            return map.put(e, PRESENT)==null;
        }

        public boolean remove(Object o) {
            return map.remove(o)==PRESENT;
        }

        public Iterator<E> iterator() {
            return map.keySet().iterator();
        }

        public String toString() {
            Iterator<E> it = iterator();
            if (! it.hasNext())
                return "[]";

            StringBuilder sb = new StringBuilder();
            sb.append('[');
            for (;;) {
                E e = it.next();
                sb.append(e == this ? "(this Collection)" : e);
                if (! it.hasNext())
                    return sb.append(']').toString();
                sb.append(',').append(' ');
            }
        }
    }
}
