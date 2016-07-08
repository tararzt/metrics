/**
 * Package Declaration
 */

package com.rzt.utils;

/**
 * Import Statements
 */

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collections;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

/**
 * This gives utility methods related to String object
 * 
 * @author sramakrishnappa
 * 
 */
public abstract class StringHelper {

	private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

	/**
	 * Protected default constructor
	 * 
	 */
	protected StringHelper()
	{
	}

	/**
	 * Formats the number
	 * 
	 * @param zeroPadded
	 * @param width
	 * @param value
	 * @return
	 */
	public static final String format( boolean zeroPadded, int width, int value )
	{
		return format(zeroPadded, width, String.valueOf(value), true);
	}

	/**
	 * Formats the string
	 * 
	 * @param zeroPadded
	 * @param width
	 * @param str
	 * @return
	 */
	public static final String format( boolean zeroPadded, int width, String str )
	{
		return format(zeroPadded, width, str, false);
	}

	/**
	 * Formats
	 * 
	 * @param zeroPadded
	 * @param width
	 * @param str
	 * @param paddingInTheFront
	 * @return
	 */
	public static final String format( boolean zeroPadded, int width, String str, boolean paddingInTheFront )
	{
		StringBuilder buffer = new StringBuilder(100);
		if( null != str && !"".equals(str) )
		{
			char paddingString = zeroPadded ? '0' : ' ';

			if( !paddingInTheFront )
			{
				buffer.append(str.substring(Math.max(0, str.length() - width)));
			}

			for( int i = str.length(); i < width; i++ )
			{
				buffer.append(paddingString);
			}

			if( paddingInTheFront )
			{
				buffer.append(str.substring(Math.max(0, str.length() - width)));
			}
		}
		return buffer.toString();
	}

	/**
	 * formats to float
	 * 
	 * @param width
	 * @param value
	 * @return
	 */
	public static final String formatToFloat( int width, int value )
	{
		return format(true, width, value) + ".00";
	}

	/**
	 * formats to float with zero padding
	 * 
	 * @param width
	 * @param value
	 * @return
	 */
	public static final String formatToFloatWithZeroPadding( int width, int value )
	{
		return format(true, width, value) + ".00";
	}

	/**
	 * formats to float without zero padding
	 * 
	 * @param width
	 * @param value
	 * @return
	 */
	public static final String formatToFloatWithoutZeroPadding( int width, int value )
	{
		return format(false, width, value) + ".00";
	}

	/**
	 * Formats
	 * 
	 * @param width
	 * @param value
	 * @return
	 */
	public static final String format( int width, int value )
	{
		return format(false, width, value);
	}

	/**
	 * Converts into upper case
	 * 
	 * @param value
	 * @return
	 */
	public static final String toUpperCase( String value )
	{
		if( value == null )
		{
			return null;
		}

		return value.toUpperCase();
	}

	public static final String toCamelCase( String s )
	{
		String newString = s;
		newString = newString.replaceAll(" ", "_");
		String[] parts = newString.split("_");
		StringBuilder camelCaseString = new StringBuilder(newString.length());
		for( String part : parts )
		{
			camelCaseString.append(toProperCase(part));
		}
		return camelCaseString.toString();
	}

	public static final String toProperCase( String s )
	{
		return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
	}

	/**
	 * Converting String to Hash with MD5
	 * 
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public static final String formatToMd5Hex( String value ) throws NoSuchAlgorithmException,
			UnsupportedEncodingException
	{
		if( value == null )
			return null;
		MessageDigest m = MessageDigest.getInstance("MD5");
		m.update(value.getBytes("iso-8859-1"), 0, value.length());
		return new BigInteger(1, m.digest()).toString(16).toUpperCase();
	}

	/**
	 * Remove all spaces from the string passed as a parameter
	 * 
	 * @param value
	 *            string to trim
	 * @return the value without any spaces character
	 */
	public static final String removeAllSpaces( String str )
	{
		int i = str.indexOf(' ');
		if( i == -1 )
		{
			return str;
		}
		else
		{
			StringTokenizer tokens = new StringTokenizer(str, " ");

			StringBuilder buffer = new StringBuilder(100);
			while( tokens.hasMoreTokens() )
			{
				buffer.append(tokens.nextToken());
			}
			return buffer.toString();
		}
	}

	/**
	 * Remove the unnecessary space characters from the string passed as a
	 * parameter.
	 * 
	 * Example: " aa aaa " --> "aa aaa"
	 * 
	 * Returns null if the parameter is null or if the trimed string is empty.
	 * 
	 * @param value
	 *            string to trim
	 * @return the value without any unnecessary space character
	 */
	public static final String trimSpaces( String value )
	{
		int index = 0;
		char current = 0;
		boolean triming = true;
		boolean appending = false;
		StringBuilder buffer = null;
		if( value == null )
		{
			return null;
		}

		buffer = new StringBuilder(value.length());

		while( index < value.length() )
		{
			current = value.charAt(index);

			if( current == ' ' )
			{
				triming = true;
			}
			else
			{
				if( triming && appending )
				{
					buffer.append(' ');
				}
				buffer.append(current);
				triming = false;
				appending = true;
			}
			++index;
		}

		if( buffer.length() == 0 )
		{
			return null;
		}

		return buffer.toString();
	}

	/**
	 * removes space, removes NonAlphaCharacters, converts to lowercase
	 * 
	 * @param str
	 * @return String
	 */
	public static final String toSimpleString( String str )
	{
		String newStr = null;
		if( null == str )
		{
			return str;
		}
		newStr = str;
		newStr = newStr.toLowerCase();
		newStr = removeNonAlphaCharacters(newStr);
		newStr = newStr.trim();

		return newStr;
	}

	/**
	 * Removes the given suffix from String
	 * 
	 * @param value
	 * @param suffix
	 * @return String
	 */
	public static final String removeSuffix( String value, String suffix )
	{
		int index = value.indexOf(suffix);
		return index < 0 ? value : value.substring(0, index);
	}

	/**
	 * Removes the given prefix from String
	 * 
	 * @param value
	 * @param prefix
	 * @return String
	 */
	public static final String removePrefix( String value, String prefix )
	{
		return value.startsWith(prefix) ? value.substring(prefix.length()) : value;
	}

	/**
	 * replaces the string with passed value
	 * 
	 * @param str
	 * @param replaceWhat
	 * @param withWhat
	 * @return
	 */
	public static final String replaceAll( String str, String replaceWhat, String withWhat )
	{
		String newStr = str;
		if( null != newStr )
		{
			int foundAtIndex = newStr.indexOf(replaceWhat);

			while( foundAtIndex >= 0 )
			{
				newStr = newStr.substring(0, foundAtIndex) + withWhat
						+ newStr.substring(foundAtIndex + replaceWhat.length());
				foundAtIndex = newStr.indexOf(replaceWhat);
			}
		}
		return newStr;
	}

	/**
	 * splits the string with passed delimeter
	 * 
	 * @param str
	 * @param delimiter
	 * @return String[]
	 */
	public static final String[] split( String str, String delimiter )
	{
		if( str != null )
		{
			StringTokenizer tokenizer = new StringTokenizer(str, delimiter);
			String[] tokens = new String[tokenizer.countTokens()];
			int i = 0;
			while( tokenizer.hasMoreElements() )
			{
				tokens[i++] = tokenizer.nextToken();
			}
			return tokens;
		}
		return new String[0];
	}

	/**
	 * 
	 * @param str
	 * @param delimiter
	 * @param considerSpaceAsToken
	 *            - StringTokenizer by default doesnot consider empty string as
	 *            a token. pass considerSpaceAsToken = true for empty string to
	 *            be considered as a token
	 * @return
	 */
	public static final String[] split( String str, String delimiter, boolean considerSpaceAsToken )
	{
		String newStr = str;
		if( null != newStr )
		{
			if( considerSpaceAsToken )
			{
				if( newStr.startsWith(delimiter) )
				{
					newStr = ' ' + delimiter;
				}

				if( newStr.endsWith(delimiter) )
				{
					newStr = newStr + ' ';
				}
				newStr = replaceAll(newStr, delimiter + delimiter, delimiter + ' ' + delimiter);
			}
			return split(newStr, delimiter);
		}

		return new String[0];
	}

	/**
	 * compares 2 String Arrays
	 * 
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static final boolean compareTwoStringArrays( String[] str1, String[] str2 )
	{
		int j = 0;
		Collections.sort(Arrays.asList(str1));
		Collections.sort(Arrays.asList(str2));

		if( str1.length == str2.length )
		{
			for( int i = 0; i < str1.length; i++ )
			{
				if( !str1[i].equals(str2[j]) )
				{
					j = 1;
					break;
				}
				j++;
			}
			return !(j == 1);
		}
		else
		{
			return false;
		}
	}

	/**
	 * checks for string array contains all null in it
	 * 
	 * @param str
	 * @return
	 */
	public static final boolean isStringArrayContainsAllNull( String[] str )
	{
		int flag = 0;
		if( null != str )
		{
			for( int i = 0; i < str.length; i++ )
			{
				if( str[i] != null )
				{
					flag = 1;
				}
			}
			return !(flag == 1);
		}
		else
		{
			return true;
		}
	}

	/**
	 * checks for null or null string
	 * 
	 * @param str
	 * @return
	 */
	public static final boolean isNullOrNullString( String str )
	{
		return null == str || "".equals(str.trim()) || "null".equals(str);
	}

	/**
	 * removes NonAlphaCharacters
	 * 
	 * @param str
	 * @return
	 */
	private static final String removeNonAlphaCharacters( String str )
	{
		char[] chars = str.toCharArray();
		for( int i = 0; i < chars.length; i++ )
		{
			char oldChar = chars[i];
			char newChar = oldChar;

			if( oldChar == '-' || oldChar == '/' || oldChar == '\\' || oldChar == '&' || oldChar == '_'
					|| oldChar == '+' || oldChar == '=' || oldChar == ':' || oldChar == '.' || oldChar == ','
					|| oldChar == '\'' || oldChar == '*' || oldChar == '|' || oldChar == '(' || oldChar == ')' )
			{
				newChar = ' ';
			}
			chars[i] = newChar;
		}
		return new String(chars);
	}

	/**
	 * break/wrap a continuous text so that the text will not extend the div
	 * element
	 * 
	 * @param indexStart
	 * @param str
	 * @return
	 */
	public static final String trimSpacesAtEveryIndex( int indexStart, String str )
	{
		int index = 0;
		StringBuilder buffer = null;
		if( str == null )
		{
			return null;
		}
		if( str.length() < 100 )
		{
			return str;
		}
		buffer = new StringBuilder(str.length());
		int startTrim = indexStart;
		while( index < str.length() )
		{
			buffer.append(str.substring(index, startTrim > str.length() ? str.length() : startTrim));
			buffer.append("  ");
			index = startTrim + 1;
			startTrim = startTrim + indexStart + 1;
		}

		return buffer.toString();

	}

	/**
	 * <p>
	 * Checks if String contains a search String, handling <code>null</code>.
	 * This method uses {@link String#indexOf(String)}.
	 * </p>
	 * 
	 * <p>
	 * A <code>null</code> String will return <code>false</code>.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.contains(null, *)     = false
	 * StringUtils.contains(*, null)     = false
	 * StringUtils.contains("", "")      = true
	 * StringUtils.contains("abc", "")   = true
	 * StringUtils.contains("abc", "a")  = true
	 * StringUtils.contains("abc", "z")  = false
	 * </pre>
	 * 
	 * @param str
	 *            the String to check, may be null
	 * @param searchStr
	 *            the String to find, may be null
	 * @return true if the String contains the search String, false if not or
	 *         <code>null</code> string input
	 * @since 2.0
	 */
	public static boolean contains( String str, String searchStr )
	{
		if( str == null || searchStr == null )
		{
			return false;
		}
		return str.indexOf(searchStr) >= 0;
	}

	/**
	 * <p>
	 * Checks if String contains a search String irrespective of case, handling
	 * <code>null</code>. This method uses {@link #contains(String, String)}.
	 * </p>
	 * 
	 * <p>
	 * A <code>null</code> String will return <code>false</code>.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.contains(null, *) = false
	 * StringUtils.contains(*, null) = false
	 * StringUtils.contains("", "") = true
	 * StringUtils.contains("abc", "") = true
	 * StringUtils.contains("abc", "a") = true
	 * StringUtils.contains("abc", "z") = false
	 * StringUtils.contains("abc", "A") = true
	 * StringUtils.contains("abc", "Z") = false
	 * </pre>
	 * 
	 * @param str
	 *            the String to check, may be null
	 * @param searchStr
	 *            the String to find, may be null
	 * @return true if the String contains the search String irrespective of
	 *         case or false if not or <code>null</code> string input
	 */
	public static boolean containsIgnoreCase( String str, String searchStr )
	{
		if( str == null || searchStr == null )
		{
			return false;
		}
		return contains(str.toUpperCase(), searchStr.toUpperCase());
	}

	public static String removeTrailingZeroFromDecimal( String value )
	{
		if( value != null )
		{
			return value.replaceAll("\\.?0*$", "");
		}

		return value;
	}

	public static Boolean isNumber( String value )
	{
		if( value == null )
			return false;
		return value.matches("^-?[0-9]\\d*(\\.\\d+)?$");
	}

	public static Boolean validateEmail( String email )
	{
		String emailreg = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
		return Pattern.matches(emailreg, email);
	}

	public static int nthOccurrence( String str, char c, int n )
	{
		int m = n;
		int pos = str.indexOf(c, 0);
		while( m-- > 1 && pos != -1 )
			pos = str.indexOf(c, pos + 1);
		return pos;
	}

	public static String randomAlphaNumeric( int wordLength )
	{
		StringBuilder builder = new StringBuilder();
		int newWordLength = wordLength;
		while( newWordLength-- != 0 )
		{
			int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
			builder.append(ALPHA_NUMERIC_STRING.charAt(character));
		}
		return builder.toString();
	}
}
