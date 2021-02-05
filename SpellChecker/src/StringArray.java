import java.util.Locale;

public class StringArray {

    private static final int DEFAULT_SIZE = 10; //Default length of array (different from size)

    private String[] elements;

    private int capacity;  //Capacity of array

    private int size;   //Current pointer

    public StringArray(){

        this.capacity = DEFAULT_SIZE;

        this.size = 0;

        elements = new String[capacity];
    }

    public StringArray(int initialCapacity) {
        //Construct string array with initial capacity specified

        try {

            capacity = initialCapacity;

            elements = new String[capacity];

        } catch (IllegalArgumentException e) {

            if (initialCapacity < 0) {

                throw new IllegalArgumentException("Capacity Negative");

            }
        }
    }


    public StringArray(StringArray a){

        this.capacity = a.size;

        this.size = a.size;

        elements = new String[capacity];

        if (a.size >= 0) System.arraycopy(a.elements, 0, elements, 0, a.size);

    }

    public int size(){

        return this.size;

    }

    public boolean isEmpty(){

        return this.size == 0;

    }

    /*
    private void ConfirmIndex(int index){
        if(index<0 || index >= size){
            throw new ArrayIndexOutOfBoundsException("IndexOutOfBoundsException! current index: "+index+", size: "+size);
        }
    }
    */

    public String get(int index){

        //ConfirmIndex(index);

        if(index<0 || index >= size)    return null;

        else   return elements[index];

    }

    public void set(int index, String s){

        //ConfirmIndex(index);

        if(index >= 0 && index < size)
            elements[index] = s;

    }


    private void confirmSize(){

        if (this.size >= this.capacity){

            int oldCapacity = this.capacity;

            this.capacity = oldCapacity*3/2; //Each time increase 1/2 of current capacity
            //this.capacity = this.capacity + DEFAULT_SIZE; //Each time increase capacity of 10 units

            String[] newElements = new String[this.capacity];

            System.arraycopy(this.elements, 0, newElements, 0, this.elements.length);

            this.elements=newElements;

        }
    }

    public void add(String s){

        confirmSize();

        this.elements[size] = s;

        this.size++;

    }

    public void insert(int index, String s){

        //ConfirmIndex(index);

        if(index == 0 && this.size==0){

            add(s);

        }else if(index >= 0 && index < size){

            this.size++;

            String[] newElements=new String[size];

            confirmSize();

            System.arraycopy(elements,0, newElements,0,index); //Copy elements before index

            newElements[index] = s;

            System.arraycopy(elements, index, newElements,index+1,size-index-1); //Copy elements after index

            elements = newElements; //Previous array pointer to new array

        }
    }

    public void remove(int index){

        //ConfirmIndex(index);

        if(index >= 0 && index < size){

            this.size--;  //Opposite tp add()

            for (int i=index ; i<this.size ; i++){

                if (i+1<elements.length){

                    elements[i] = elements[i+1];

                }
            }
        }
    }

    public boolean contains(String s){

        for(String tmp: this.elements){

            if(s.equalsIgnoreCase(tmp))    return true;

        }

        return false;

    }

    // Improved version, only for sorted data
    public boolean binarySearchContains(String s){

        int low = 0;
        int high = size-1;
        while(low<=high) {
            int mid = (low+high)/2;
            if(elements[mid].equalsIgnoreCase(s)) {
                return true;
            }else if(elements[mid].toLowerCase().compareTo(s.toLowerCase())>0) {
                high = mid-1;
            }else {
                low = mid+1;
            }
        }
        return false;
    }

    public boolean containsMatchingCase(String s){

        for(String tmp: this.elements){

            if(s.equals(tmp))    return true;

        }

        return false;

    }

    public int indexOf(String s){

        for(int i=0 ; i<this.size ; i++){

            if(s.equalsIgnoreCase(elements[i]))    return i;

        }

        return -1;

    }

    public int indexOfMatchingCase(String s){

        for(int i=0 ; i<this.size ; i++){

            if(s.equals(elements[i]))    return i;

        }

        return -1;

    }

}
