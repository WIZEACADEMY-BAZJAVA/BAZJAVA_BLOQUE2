package com.wizeline.maven.LearningJava.utils;

import com.wizeline.maven.LearningJava.enums.AccountType;
import com.wizeline.maven.LearningJava.enums.Country;
import com.wizeline.maven.LearningJava.model.BankAccountDTO;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.util.*;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.security.*;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Utils {

	// Definicion del patron para validar contraseña
	/**
	 * ^           Indica el inicio de la declaracion
	 * (?=.*[0-9]) Debe contener un digito del 1 al 9
	 * (?=.*[a-z]) Debe contener una letra minuscula
	 * (?=.*[A-Z]) Debe contener una letra mayuscula
	 * (?=.*[@#$]) Debe contener un caracter especial de los indicados entre corchetes
	 * {6,8}       Debe tener una longitud de entre 6 a 8 caracteres
	 * $		   Indica el final de la declaracion
	 */
	private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$]).{6,8}$";
	private static final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

	// Definicion del patron para validar formato de fecha (dd-mm-yyyy)
	private static Pattern DATE_PATTERN = Pattern.compile("^\\d{2}-\\d{2}-\\d{4}$");

	
	public static String getString(String value) {
		if(value != null) {
			return value;
		}
		return "";
	}
	
	public static boolean validateNullValue(String value) {
		if(value != null) {
			return true;
		}
		return false;
	}

	public static boolean isPasswordValid(String password) {
		// Valida la contraseña contra el patron que definimos
		Matcher matcher = pattern.matcher(password);
		return matcher.matches();
	}

	public static boolean isDateFormatValid(String date) {
		// Valida la fecha contra el patron que definimos
		return DATE_PATTERN.matcher(date).matches();
	}

	public static long randomAcountNumber() {
		return new Random().nextLong();
	}

	public static double randomBalance() {
		return new Random()
				.doubles(1000, 9000)
				.limit(1)
				.findFirst()
				.getAsDouble();
	}

	public static AccountType pickRandomAccountType() {
		// Creando un arreglo a partir de valores ya definidos
		AccountType[] accountTypes = AccountType.values();
		return accountTypes[new Random().nextInt(accountTypes.length)];
	}

	public static String randomInt() {
		return String.valueOf(new Random().ints(1, 10)
				.findFirst()
				.getAsInt());
	}

	public static String getCountry(Country country) {
		Map<Country, String> countries = new HashMap<>();
		countries.put(Country.US, "United States");
		countries.put(Country.MX, "Mexico");
		countries.put(Country.FR, "France");
		return countries.get(country);
	}

	public static List<BankAccountDTO> cypherAccounts(List<BankAccountDTO> accounts){
		byte[] keyBytes = new byte[]{
				0x01, 0x23, 0x45, 0x67, (byte) 0x89, (byte) 0xab, (byte) 0xcd, (byte) 0xef
		};
		byte[] ivBytes = new byte[]{
				0x00, 0x01, 0x02, 0x03, 0x00, 0x00, 0x00, 0x01
		};

		Security.addProvider(new BouncyCastleProvider());
		SecretKeySpec key = new SecretKeySpec(keyBytes, "DES");
		IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
		Cipher cipher = null;

		try {
			cipher = Cipher.getInstance("DES/CTR/NoPadding", "BC");
			cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
			// Cifraremos solamente el nombre y el country
			for (int i = 0; i < accounts.size(); i++) {
				String accountName = accounts.get(i).getAccountName();
				byte[] arrAccountName = accountName.getBytes();
				byte [] accountNameCipher = new byte[cipher.getOutputSize(arrAccountName.length)];
				int ctAccountNameLength = cipher.update(arrAccountName, 0, arrAccountName.length, accountNameCipher, 0);
				ctAccountNameLength += cipher.doFinal(accountNameCipher, ctAccountNameLength);
				accounts.get(i).setAccountName(accountNameCipher.toString());

				String accountCountry = accounts.get(i).getCountry();
				byte[] arrAccountCountry = accountCountry.getBytes();
				byte[] accountCountryCipher = new byte[cipher.getOutputSize(arrAccountCountry.length)];
				int ctAccountCountryLength = cipher.update(arrAccountCountry, 0, arrAccountCountry.length, accountCountryCipher, 0);
				ctAccountNameLength += cipher.doFinal(accountCountryCipher, ctAccountCountryLength);
				accounts.get(i).setCountry(accountCountryCipher.toString());

			}
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		} catch (NoSuchProviderException e) {
			throw new RuntimeException(e);
		} catch (NoSuchPaddingException e) {
			throw new RuntimeException(e);
		} catch (InvalidAlgorithmParameterException e) {
			throw new RuntimeException(e);
		} catch (ShortBufferException e) {
			throw new RuntimeException(e);
		} catch (IllegalBlockSizeException e) {
			throw new RuntimeException(e);
		} catch (BadPaddingException e) {
			throw new RuntimeException(e);
		} catch (InvalidKeyException e) {
			throw new RuntimeException(e);
		}
		return accounts;
	}

	public static Map<String, String> convertToMap(String data){
		data = data.replace("\"","");
		data = data.substring(1, data.length()-1);
		String[] keyValuePairs = data.split(",");
		Map<String,String> map = new HashMap<>();

		for(String pair : keyValuePairs) {
			String[] entry = pair.split(":");
			map.put(entry[0].trim(), entry[1].trim());
		}
		return map;
	}

	public static <T> void muestraMensajeConsola(ArrayList<T> datos){
		datos.stream().forEach((elemento) ->{
			System.out.print(elemento+"");
		});
	}
}
