import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;
import java.util.Date;
import java.text.SimpleDateFormat;
class Server
{
	public static void main(String dt[])
	{
		ServerSocket sskt=null;
		Socket skt=null;
		DataInputStream dis=null;
		DataOutputStream dos=null;
		FileInputStream fis=null;
		try
		{
			sskt=new ServerSocket(4444);
			System.out.println("Waiting for a Client");
			skt=sskt.accept();
			System.out.println("Client Connected");
			dis=new DataInputStream(skt.getInputStream());
			dos=new DataOutputStream(skt.getOutputStream());
			final String username="Rohan";
			final String password="Rohan";	
			String str=dis.readUTF();
			String arr[]=str.split(";");
			if((username.equals(arr[0]))&&(password.equals(arr[1])))
			{
				dos.writeBoolean(true);
				do
				{
					String choice=dis.readUTF();
					switch(choice)
					{
						case "1":
						{
							String filename=dis.readUTF();
							File f=new File(filename);//File Class Important !!!
							if(f.exists())
							{
								dos.writeBoolean(true);
								dos.writeLong(f.length());
								System.out.println("Downloading Begins.........");
								fis=new FileInputStream(filename);
								byte b[]=new byte[4096];
								int c=0;
								while((c=fis.read(b))!=-1)
								{
									dos.write(b,0,c);
								}
								fis.close();
								System.out.println("File Sent Successfully");
							}	
							else
							{
								dos.writeBoolean(false);
								System.out.println("File Not Found!");
							}
							break;
						}	
						case "2":
						{
							if(dis.readBoolean())
							{
								String filename=dis.readUTF();
								int size=(int)dis.readLong();
								System.out.println("Uploading Begins...");
								FileOutputStream fos=new FileOutputStream(filename);
								byte b[]=new byte[4096];
								int c=0;
								for(c=0;c<=size;c=c+1024)
								{
									c=dis.read(b);
									fos.write(b,0,c);
								}
								System.out.println("File Recieved!");
								fos.close();
							}
							else
							{
								System.out.println("File Not Found!!");
							}
							break;
						}
						case "3":
						{
							try
							{
								skt.close();
								sskt.close();
								System.out.println("Client Disconnected");
								System.exit(0);
							}
							catch(Exception e)
							{
								
							}
						}
					}
				}while(true);
			}	
			else
			{
				dos.writeBoolean(false);
				System.out.println("Invalid User");
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
				dos.close();
				dis.close();
			}
			catch(Exception e)
			{
				System.out.println(e);
			}
		}
	}
}