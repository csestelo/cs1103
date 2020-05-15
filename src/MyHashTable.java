class Item {
    public String key;
    public String value;
    public Item next;

    Item(String key, String value, Item next) {
        this.key = key;
        this.value = value;
        this.next = next;
    }
}

class MyLinkedList {
    private Item firstItem;

    String get(String key) {
        for (Item i = firstItem; i != null; i = i.next) {
            if (i.key.equals(key)) {
                return i.value;
            }
        }
        return null;
    }

    void put(String key, String value) {
        for (Item i = firstItem; i != null; i = i.next) {
            if (i.key.equals(key)) {
                i.value = value;
                return;
            }
        }
        firstItem = new Item(key, value, firstItem);
    }

    void remove(String key) {
        // guaranteed to not be null by MyHashMap.remove()
        if (firstItem.key.equals(key)) {
            firstItem = firstItem.next;
            return;
        }

        Item parent = firstItem;
        for (Item i = firstItem.next; i != null; parent = i, i = i.next) {
            if (i.key.equals(key)) {
                parent.next = i.next;
            }
        }
    }
}

class MyHashTable {
    private final int capacity;
    private int size = 0;
    MyLinkedList[] table;

    MyHashTable(int capacity) {
        this.capacity = capacity;
        table = new MyLinkedList[capacity];
        for (int i = 0; i < table.length; i++) {
            table[i] = new MyLinkedList();
        }
    }

    String get(String key) {
        return table[createHash(key)].get(key);
    }

    String put(String key, String value) {
        String oldValue = get(key);
        table[createHash(key)].put(key, value);
        if (oldValue == null) {
            size++;
        }
        return oldValue;
    }

    String remove(String key) {
        String currentValue = get(key);
        if (currentValue != null) {
            table[createHash(key)].remove(key);
            size--;
        }
        return currentValue;
    }

    boolean contains(String key) {
        return get(key) != null;
    }

    int size() {
        return size;
    }

    private int createHash(String key) {
        return Math.abs(key.hashCode()) % capacity;
    }
}

class TestMapTable {
    public static void main(String[] args) {
        testMap(new MyHashTable(1));
        testMap(new MyHashTable(10));
    }

    public static void testMap(MyHashTable myMap) {
        assert myMap.get("hello") == null;
        assert myMap.size() == 0;
        assert !myMap.contains("hello");
        assert myMap.remove("hello") == null;
        assert myMap.size() == 0;

        assert myMap.put("hello", "world") == null;
        assert myMap.size() == 1;
        assert myMap.get("hello").equals("world");
        assert myMap.contains("hello");

        assert myMap.put("hello", "planet").equals("world");
        assert myMap.size() == 1;
        assert myMap.get("hello").equals("planet");
        assert myMap.contains("hello");

        assert myMap.put("bye", "ocean") == null;
        assert myMap.size() == 2;
        assert myMap.get("bye").equals("ocean");
        assert myMap.contains("bye");
        assert myMap.remove("bye").equals("ocean");
        assert myMap.get("bye") == null;
        assert !myMap.contains("bye");
        assert myMap.size() == 1;

        assert myMap.remove("hello").equals("planet");
        assert myMap.get("hello") == null;
        assert !myMap.contains("hello");
        assert myMap.size() == 0;
    }
}