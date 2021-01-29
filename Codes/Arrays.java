int[] a = new int[3];
a[0] = 3;
a[1] = 9;
a[2] = 8;

MyDate[] dates = new MyDate[3];
dates[0] = new MyDate(22, 7, 1964);
dates[1] = new MyDate(1, 1, 2000);
dates[2] = new MyDate(1, 22, 2002);

//静态初始化
int[] a ={3,9,8};
int[]a = new int[]{3, 9, 8};
int[][] a ={{1,2},{3,4,0,9},{5,6,7}};

MyDate[] dates ={
    new MyDate(22, 7, 1964),
    new MyDate(1, 1, 2000),
    ew MyDate(1, 22, 2002)
};

//动态初始化：只指定长度，由系统给出初始化值(0, "", false)
int[] arrs=new int[5]; 
int[][] arrs = new int[8][4]; 

int[][] t = new int[3][];
t[0] = new int [][2];
t[1] = new int [][4];
t[2] = new int [][3];