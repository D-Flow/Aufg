public class Sort{
	
	public static void BubbleSort(int[] arr){
		int n = arr.length-1;
		for(int i = 0;i<=n;i++){
			assert isPartialSorted(arr,0,i);	// da minimum zu i bewegt wird
			for(int j = n;j>=i+1;j--)
				if(arr[j-1]>arr[j])
					swap(arr,j-1,j);
		}
			
	}
	
	public static void swap(int[] arr,int a,int b){
		int t = arr[a];
		arr[a]=arr[b];
		arr[b]=t;
	}
	
	public static int[] fill(int amt){	//Initialisierung absteigend
		int[] arr=new int[amt];
		for(int i = amt-1;i>0;i--)
			arr[i]=amt-i;
		return arr;
	}
	
	public static boolean isPartialSorted(int[] arr,int a,int b){
		for(int i = a;i<b-1;i++)
			if(arr[i]>arr[i+1])
				return false;
		return true;
	}
	
	public static boolean isSorted(int[] arr){
		for(int i = 0;i<arr.length-1;i++)
			if(arr[i]>arr[i+1])
				return false;
		return true;
	}
	
	public static void main(String ... a)throws Exception{
		if(a.length==0){
			int[] arr=fill(50000);
			
			long start=System.currentTimeMillis();
			BubbleSort(arr);
			long end = System.currentTimeMillis();
			
			System.out.println("Delta : "+(end-start)+"ms");
			System.out.println("Delta : "+(float)(end-start)/1000+"s");
			
			if(isSorted(arr))
				System.out.println("Array ist Sortiert!");
			else
				System.out.println("Array ist NICHT Sortiert!");
		}else{
			float TIME=Float.parseFloat(a[0]);
			System.out.println("Suche n für "+TIME+"s");
			int n = 1000;
			while(timeSort(n)<TIME)
				n=n*2;
			System.out.println("Maximales n : "+n);
			int left=n/2,right=n;
			System.out.println("N ist ungefähr : "+search(left,right,TIME));
		}

	}
	
	public static float timeSort(int amt){
		int[] arr=fill(amt);
		
		long start=System.currentTimeMillis();
		BubbleSort(arr);
		long end = System.currentTimeMillis();
		
		arr=null;
		//Laufzeit in ms
		
		return (float)(end-start)/1000;	//Gleitkommazahl in sekunden
	}
	
	public static int search(int left,int right,float time){
		int m = left+(right-left)/2;	// Mitte
		float messuredTime=timeSort(m);	// Zeit für bubblesort auf m elementen in s
		
		System.out.println("Suche zwischen : "+left+" "+right);
		System.out.println("N : "+m+" Zeit : "+messuredTime);
		
		if(messuredTime<=time+0.1f&&messuredTime>=time-0.1f)
			return m;
		
		if(messuredTime>time)
			return search(left,m,time);
		else
			return search(m+1,right,time);

	}

}
