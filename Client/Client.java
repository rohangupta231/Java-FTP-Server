import java.net.Socket;
import java.io.*;
class Client
{
	public static void main(String dt[])
	{	
		Socket skt=null;
		InputStreamReader isr=null;
		BufferedReader br=null;
		DataOutputStream dos=null;//important classes
		DataInputStream dis=null;
		try
		{
			skt=new Socket("127.0.0.1",1999);
			System.out.println("Connected to Server");
			isr=new InputStreamReader(System.in);
			br=new BufferedReader(isr);
			dos=new DataOutputStream(skt.getOutputStream());
			dis=new DataInputStream(skt.getInputStream());
			System.out.println("Enter Username");
			String username=br.readLine();
			System.out.println("Enter password");
			String password=br.readLine();
			dos.writeUTF(username+";"+password);
			if(dis.readBoolean())
			{
					do
					{
						System.out.println("1.Download a File");
						System.out.println("2. Upload a File");
						System.out.println("3.Exit");
						System.out.println("Please! Select a Choice");
						String choice=br.readLine();
						dos.writeUTF(choice);
						switch(choice)
						{
							case "1":
							{
								System.out.println("Enter File Name");
								String filename=br.readLine();
								dos.writeUTF(filename);//Unicode Methods	
								if(dis.readBoolean())
								{
									int size=(int)dis.readLong();
									System.out.println("Downloading Begins......");
									FileOutputStream fos=new FileOutputStream(filename);
									byte b[]=new byte[4096];
									int c=0;
									for(c=0;c<=size;c=c+1024)
									{
										c=dis.read(b);
										fos.write(b,0,c);
									}
									fos.close();
									System.out.println("File Downloaded Successfully");
								}
								else
								{
									System.out.println("File Not Found");
								}
								break;
							}
							case "2":
							{
								System.out.println("Enter File Name");
								String filename=br.readLine();
								File f=new File(filename);
								if(f.exists())
								{
									dos.writeBoolean(true);
									dos.writeUTF(filename);
									dos.writeLong(f.length());
									System.out.println("Uploading Begins........");
									FileInputStream fis=new FileInputStream(filename);
									byte b[]=new byte[4096];
									int c=0;
									while((c=fis.read(b))!=-1)
									{
										dos.write(b,0,c);
										//System.out.println("Hello");
									}		
									System.out.println("File updated Successfully");
								}
								else
								{
									System.out.println("File Not Found");
									dos.writeBoolean(false);
								}
								break;
							}
							case "3":
							{
								
								
								
									System.out.println("Thank You");
									System.exit(0);
								
							}
						}
					}while(true);
			}
			else
			{
				System.out.println("Wrong Password");
			}
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		finally
		{
			try
			{	
				dis.close();
				dos.close();
				br.close();
				isr.close();
			}
			catch(Exception e)
			{
				System.out.println(e);
			}
		}
	}
}