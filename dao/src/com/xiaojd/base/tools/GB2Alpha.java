package com.xiaojd.base.tools;

import net.sourceforge.pinyin4j.PinyinHelper;

import com.sun.org.apache.xalan.internal.xsltc.runtime.Hashtable;

@SuppressWarnings("restriction")
public class GB2Alpha {

	// 字母Z使用了两个标签，这里有２７个值
	// i, u, v都不做声母, 跟随前面的字母
	static private char[] chartable = { '啊', '芭', '擦', '搭', '蛾', '发', '噶', '哈',
			'哈', '击', '喀', '垃', '妈', '拿', '哦', '啪', '期', '然', '撒', '塌', '塌',
			'塌', '挖', '昔', '压', '匝', '座' };

	static private char[] alphatable = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
		 	'I','J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V',
			'W', 'X', 'Y', 'Z' };

	static private int[] table = new int[27];

	// 初始化
	{
		for (int i = 0; i < 27; ++i) {
			table[i] = gbValue(chartable[i]);
		}
	}

	public GB2Alpha() {

	}

	// 主函数,输入字符,得到他的声母,
	// 英文字母返回对应的大写字母
	// 其他非简体汉字返回 '0'

	static public Hashtable aa = new Hashtable();
	static public String[][] w = { { "蛭", "Z" }, { "苷", "G" }, { "哌", "P" },
			{ "螯", "A" }, { "醌", "K" }, { "钴", "G" }, { "呤", "L" },
			{ "橼", "Y" }, { "枸", "J" }, { "痫", "X" }, { "睾", "G" },
			{ "喋", "D" }, { "胱", "G" }, { "萘", "Z" }, { "嗪", "Q" },
			{ "拮", "J" }, { "喃", "N" }, { "莨", "L" }, { "锂", "L" },
			{ "呋", "F" }, { "酰", "X" }, { "孢", "B" }, { "酯", "Z" },
			{ "膦", "L" }, { "啶", "D" }, { "唑", "Z" }, { "祛", "Q" },
			{ "溴", "X" }, { "咪", "M" }, { "茴", "H" }, { "铵", "A" },
			{ "羧", "S" }, { "黏", "N" }, { "榈", "L" }, { "羟", "Q" },
			{ "胍", "G" }, { "蒿", "H" }, { "脲", "N" }, { "蒽", "E" },
			{ "苄", "B" }, { "砜", "F" }, { "吲", "Y" }, { "珀", "P" },
			{ "琥", "H" }, { "嘧", "M" }, { "菪", "D" }, { "胂", "S" },
			{ "鞣", "R" }, { "缬", "X" }, { "芸", "Y" }, { "袢", "P" },
			{ "酐", "G" }, { "肽", "T" }, { "肼", "J" }, { "茚", "Y" },
			{ "芴", "W" }, { "喹", "K" }, { "吡", "B" }, { "骼", "G" },
			{ "锶", "S" }, { "噻", "S" }, { "甾", "Z" }, { "啉", "L" },
			{ "檗", "B" }, { "癫", "D" }, { "脒", "M" }, { "铋", "B" },
			{ "蚓", "Y" }, { "嘌", "P" }, { "泮", "P" }, { "巯", "Q" },
			{ "哚", "D" }, { "厥", "J" }, { "肟", "W" }, { "噁", "E" },
			{ "蟅", "Z" }, { "薢", "X" }, { "α", "A" }, { "β", "B" },
			{ "尪", "W" }, { "瘾", "Y" }, { "瘿", "Y" }, { "芙", "F" },
			{ "荟", "K" }, { "苁", "C" }, { "麝", "S" }, { "芪", "Q" },
			{ "栀", "Z" }, { "逍", "X" }, { "杞", "Q" }, { "蠲", "S" },
			{ "匮", "K" }, { "橘", "J" }, { "藜", "L" }, { "芨", "J" },
			{ "蔹", "L" }, { "蒌", "L" }, { "茱", "Z" }, { "萸", "Y" },
			{ "蜈", "W" }, { "蚣", "S" }, { "戟", "J" }, { "芫", "Y" },
			{ "蟾", "C" }, { "瘀", "Y" }, { "蝥", "C" }, { "佗", "T" },
			{ "藿", "H" }, { "癃", "L" }, { "苓", "L" }, { "萆", "P" },
			{ "枇", "B" }, { "杷", "B" }, { "蓼", "L" }, { "痧", "S" },
			{ "茯", "F" }, { "癖", "P" }, { "斛", "F" }, { "翳", "Y" },
			{ "芩", "L" }, { "瘘", "L" }, { "葆", "B" }, { "酊", "D" },
			{ "腙", "Z" }, { "槿", "J" }, { "钛", "T" }, { "酏", "Y" },
			{ "吖", "Y" }, { "痱", "F" }, { "莪", "W" }, { "蝮", "F" },
			{ "钆", "Z" }, { "镱", "Y" }, { "铊", "T" }, { "菁", "J" },
			{ "芎", "H" }, { "鲨", "S" }, { "痤", "Z" }, { "黛", "D" },
			{ "囗", "K" }, { "脘", "W" }, { "荞", "Q" }, { "枳", "Z" },
			{ "呱", "G" }, { "蔻", "K" }, { "窦", "D" }, { "癀", "H" },
			{ "荨", "X" }, { "猕", "S" }, { "癜", "D" }, { "瘢", "B" },
			{ "贲", "B" }, { "疖", "J" }, { "衄", "N" }, { "疣", "Y" },
			{ "髌", "B" }, { "猝", "C" }, { "疱", "P" }, { "搦", "N" },
			{ "骶", "D" }, { "髂", "Q" }, { "呃", "E" }, { "腓", "F" },
			{ "疝", "S" }, { "瘙", "S" }, { "腱", "J" }, { "肱", "G" },
			{ "佝", "G" }, { "偻", "L" }, { "颌", "G" }, { "踝", "H" },
			{ "疸", "D" }, { "瘭", "B" }, { "胛", "J" }, { "睑", "J" },
			{ "胫", "J" }, { "坶", "M" }, { "潴", "Z" }, { "颞", "N" },
			{ "胬", "N" }, { "龈", "Y" }, { "胼", "P" }, { "胝", "Z" },
			{ "桡", "R" }, { "骰", "T" }, { "霰", "X" }, { "阙", "Q" },
			{ "壅", "Y" }, { "癔", "Y" }, { "劂", "J" }, { "跖", "Z" },
			{ "穹", "Q" } };
	static {
		for (int i = 0; i < w.length; i++) {
			aa.put(w[i][0], w[i][1]);
		}
	}

	static public char Char2Alpha(char ch) {
		if (aa.get(ch + "") != null)
			return aa.get(ch + "").toString().charAt(0);
		if (ch >= 'a' && ch <= 'z')
			return (char) (ch - 'a' + 'A');
		if (ch >= 'A' && ch <= 'Z')
			return ch;

		int gb = gbValue(ch);
		if (gb < table[0])
			return ch;

		int i;
		for (i = 0; i < 26; ++i) {
			if (match(i, gb))
				break;
		}

		if (i >= 26) {
			if (aa.get(ch + "") == null)
				aa.put(ch + "", "");
			System.out.println(ch);
			return '0';
		} else
			return alphatable[i];
	}

	// 根据一个包含汉字的字符串返回一个汉字拼音首字母的字符串
	static public String String2Alpha(String SourceStr) {
		if (SourceStr==null) 
			return "";
		String Result = "";
		int StrLength = SourceStr.length();
		int i;
		for (i = 0; i < StrLength; i++) {
			try {
				if((SourceStr.charAt(i) >= 48 && SourceStr.charAt(i) <= 57) || (SourceStr.charAt(i) >= 65 && SourceStr.charAt(i) <= 90) || (SourceStr.charAt(i) >= 97 && SourceStr.charAt(i) <= 122)){
					Result += SourceStr.charAt(i)+"".toUpperCase();
				}else{
					Result += PinyinHelper.toHanyuPinyinStringArray(SourceStr.charAt(i))[0].toUpperCase().charAt(0);
				}
//				Result += Char2Alpha(SourceStr.charAt(i));
			} catch (Exception e) {
				Result += ' ';
			}
		}
		return Result;
	}

	static private boolean match(int i, int gb) {
		if (gb < table[i])
			return false;

		int j = i + 1;

		// 字母Z使用了两个标签
		while (j < 26 && (table[j] == table[i]))
			++j;

		if (j == 26)
			return gb <= table[j];
		else
			return gb < table[j];

	}

	// 取出汉字的编码
	static private int gbValue(char ch) {
		String str = new String();
		str += ch;
		try {
			byte[] bytes = str.getBytes("GB2312");
			if (bytes.length < 2)
				return 0;
			return (bytes[0] << 8 & 0xff00) + (bytes[1] & 0xff);
		} catch (Exception e) {
			return 0;
		}

	}

	static {
		new GB2Alpha();
	}

	public static void main(String[] args) {
		System.out.println(String2Alpha("阿胶胶囊"));
	}
}