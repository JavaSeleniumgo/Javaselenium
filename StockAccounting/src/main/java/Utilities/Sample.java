package Utilities;

public class Sample
{
	public static void main(String []args) throws Throwable
	{
		Excelfileutil excel= new Excelfileutil();
		int gv=excel.rowCount("Sheet1");
		System.out.println(gv);
		
		int vg=excel.colCount("Sheet1", 0);
		System.out.println(vg);
	}

}
