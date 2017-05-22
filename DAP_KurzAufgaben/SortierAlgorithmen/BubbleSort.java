/**
 *
 */
public class BubbleSort {
    public static void bubbleSort(int[] arr){
        int n = arr.length-1;
        for(int i = 0;i<=n;i++){
            assert SortUtil.isPartialSorted(arr,0,i);	// da minimum zu i bewegt wird
            for(int j = n;j>=i+1;j--)
                if(arr[j-1]>arr[j])
                    swap(arr,j-1,j);
        }

    }
    private static void swap(int[] arr,int a,int b){
        int t = arr[a];
        arr[a]=arr[b];
        arr[b]=t;
    }
}
