package product_management;

import java.sql.ResultSet;
import java.util.Scanner;
import db_operations.DBUtils;

public class ProductManagement {
	public static void productManagement()
	{
		Scanner sc = new Scanner(System.in);
		boolean canIKeepRunningTheProgram = true;
		while(canIKeepRunningTheProgram == true) 
		{
			System.out.println("\n Welcome to product management !");
			System.out.println("\n Select one of the options given below :");
			System.out.println("1. Add product : ");
			System.out.println("2. Edit product : " );
			System.out.println("3. Delete product : ");
			System.out.println("4. Search product : ");
			System.out.println("5. Quit");
			int optionSelected = sc.nextInt();
			if(optionSelected == ProductOptions.QUIT_PRODUCT)
			{
				System.out.println("\n Product management application closed !");
				canIKeepRunningTheProgram = false;
			}
			else if(optionSelected == ProductOptions.ADD_PRODUCT) 
			{
				addProduct();
			}
			else if(optionSelected == ProductOptions.SEARCH_PRODUCT) 
			{
				System.out.println("\n Enter product to be searched : \n"); 
				sc.nextLine();                                         
				String searchProductName = sc.nextLine();
				searchProduct(searchProductName);
			}
			else if(optionSelected == ProductOptions.DELETE_PRODUCT) 
			{
				System.out.println("\n Enter product to be deleted : \n");
				sc.nextLine();                                      
				String deleteProductName = sc.nextLine();
				deleteProduct(deleteProductName);
				System.out.println("\n Product deleted sucessfully.");
			}
			else if(optionSelected == ProductOptions.EDIT_PRODUCT) 
			{
				System.out.println("\n Enter product to be edited : ");
				sc.nextLine();                                    
				String editProductName = sc.nextLine();
				editProduct(editProductName);
				System.out.println("\n product edited successfully !");
			}
		}
	}
	public static void addProduct()
	{
		Product productObject = new Product();
		Scanner sc = new Scanner(System.in);

		System.out.println("\n Product id : ");
		productObject.productId = sc.nextLine();

		System.out.println(" Product name : ");
		productObject.productName = sc.nextLine();

		System.out.println(" Product price : ");
		productObject.productPrice = sc.nextLine();

		System.out.println(" Product quantity : ");
		productObject.productQuantity = sc.nextLine();

		System.out.println(" Product category : ");
		productObject.productCategory = sc.nextLine();

		System.out.println("\n Product details are : \n");
		System.out.println("Product id is : "+productObject.productId);
		System.out.println("Product name is : "+productObject.productName);
		System.out.println("Product price is : "+productObject.productPrice);
		System.out.println("Product quantity is : "+productObject.productQuantity);
		System.out.println("Product category is : "+productObject.productCategory);

		String query = "INSERT INTO product(product_id, product_name, product_price, product_quantity, product_category) VALUES ('"
				+ productObject.productId + "', '" + productObject.productName + "', '" + productObject.productPrice + "','"
				+ productObject.productQuantity + "','" + productObject.productCategory + "')";
		DBUtils.executeQuery(query);
	}

	public static void searchProduct(String productName)
	{
		String query = "select * from product where product_name ='"+productName+ "' ";
		ResultSet rs = DBUtils.executeQueryGetResult(query);
		try 
		{
			while(rs.next())
			{
				if(rs.getString("product_name").equalsIgnoreCase(productName))
				{
					System.out.println(" Product id is : "+ rs.getString("product_id"));
					System.out.println(" Product name is : "+ rs.getString("product_name"));
					System.out.println(" Product price is : "+ rs.getString("product_price"));
					System.out.println(" Product quantity is : "+ rs.getString("product_quantity"));
					System.out.println(" Product category is : "+ rs.getString("product_category"));
					return;
				}
			}
		}catch(Exception e)
		{
			System.out.println("\n Product not found.");
		}
	}
	public static void deleteProduct(String productName)
	{
		String query = "delete from product where product_name = '"+productName+"' ";
		DBUtils.executeQuery(query);
	}

	public static void editProduct(String productName) {

		String query = "select * from product where product_name='"+productName+"' ";
		ResultSet rs = DBUtils.executeQueryGetResult(query);

		try {
			while (rs.next()) 
			{ 
				if (rs.getString("product_name").equalsIgnoreCase(productName)) {
					Scanner scanner = new Scanner(System.in);
					Product product = new Product();

					System.out.println("Editing product: "+productName);

					System.out.print("New product id: ");
					product.productId = scanner.nextLine();

					System.out.print("New product name: ");
					product.productName = scanner.nextLine();

					System.out.print("New product price: ");
					product.productPrice = scanner.nextLine();

					System.out.print("New product quantity: ");
					product.productQuantity = scanner.nextLine();

					System.out.print("New product category: ");
					product.productCategory = scanner.nextLine();

					String updateQuery = "update product set" + "product_id='"+product.productId+"', "+"product_name = '"+product.productName+"', "
							+ "product_price='"+product.productPrice+"', product_quantity='"+product.productQuantity+"', "+ "product_category='"+
							product.productCategory+"' where product_name='"+rs.getString("product_name")+"'";

					DBUtils.executeQuery(updateQuery);
					return;
				}
			}
		} 
		catch (Exception e) {
			System.out.println("Product not found.");
		}
	}

}

