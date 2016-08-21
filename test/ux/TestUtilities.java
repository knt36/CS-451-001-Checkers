package ux;

import org.junit.Assert;
import ux.Screens.ScrFactory;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class TestUtilities {
	
	public static void applyDictionary(Component c){
		File f = new File("TestWords");
		try{
			Scanner sc = new Scanner(f);
			if (c instanceof JTextField){
				while(sc.hasNextLine()){
					JTextField textfield = ((JTextField)c);
					textfield.setText(sc.nextLine());
				}
			}
		}catch(FileNotFoundException e){
			Assert.fail("Failed to get test file");
		}catch(Exception e){
			Assert.fail("Dictionary Test Failed!");
			
		}
	}
	
	public static void applyDictionaryScreen(ScrFactory scr){
		File f = new File("TestWords");
		for(int i = 0 ; i < scr.getComponentCount(); i++){
			Component c = scr.getComponent(i);
			try{
				
				Scanner sc = new Scanner(f);
				//SELECTOR ACTION TO DUMP DICTIONARY
				if (c instanceof JTextField){
					while(sc.hasNextLine()){
						JTextField textfield = ((JTextField)c);
						textfield.setText(sc.nextLine());
					}
				}else if(c instanceof ScrFactory){
					applyDictionaryScreen(((ScrFactory)c));
				}
			}catch(FileNotFoundException e){
				Assert.fail("Failed to get test file");
			}catch(Exception e){
				Assert.fail("Dictionary Test Failed!");
				
			}
		}
		}

	public static void applyButtonPath(ScrFactory begin, ScrFactory end){
		
	}
}
