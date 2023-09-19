              @Override
        public boolean hasPrevious() {
            return index > 0;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            E result = current.element;
            current = current.next;
            index++;
            nextCalled = true;
            return result;
        }

        @Override
        public int nextIndex() {
            return index;
        }

        @Override
        public E previous() {
            if (!hasPrevious()) {
                throw new NoSuchElementException();
            }
            if (nextCalled) {
                current = current.previous;
            }
            nextCalled = false;
            E result = current.element;
            current = current.previous;
            index--;
            return result;
        }

        @Override
        public int previousIndex() {
            return index - 1;
        }

        @Override
        public void remove() {
            if (nextCalled) {
                Node<E> prevNode = current.previous;
                Node<E> nextNode = current.next;
                if (prevNode != null) {
                    prevNode.next = nextNode;
                } else {
                    head = nextNode;
                }
                if (nextNode != null) {
                    nextNode.previous = prevNode;
                } else {
                    tail = prevNode;
                }
                current = prevNode;
            } else {
                throw new IllegalStateException();
            }
            size--;
        }

        @Override
        public void set(E e) {
            if (nextCalled) {
                current.element = e;
            } else {
                throw new IllegalStateException();
            }
        }

        @Override
        public void add(E e) {
            Node<E> newNode = new Node<>(e);
            newNode.next = current;
            newNode.previous = current.previous;
            if (current.previous != null) {
                current.previous.next = newNode;
            } else {
                head = newNode;
            }
            current.previous = newNode;
            current = newNode;
            index++;
            size++;
            nextCalled = false;
        }
    }

