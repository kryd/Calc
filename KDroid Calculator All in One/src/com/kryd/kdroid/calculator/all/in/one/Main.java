package com.kryd.kdroid.calculator.all.in.one;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import android.os.Bundle;
import android.os.Vibrator;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

//#DCFFCF
public class Main extends Activity {

	private static final String DEFAULT_TEXT = "0";
	private static final int DEFAULT_LENGTH = 12;
	private static final Long MAX_NUM = Long.parseLong("9999999999");
	private static final Long MIN_NUM = Long.parseLong("-9999999999");
	private int actionCount = 0;
	private Action prevAction = null;
	private Vibrator vibe;
	private DecimalFormat myFormat = new DecimalFormat("#.##########");
	private NumberFormat maxNumFormat = new DecimalFormat("#.########E0");
	public static final String tag = "Kryd";

	public enum Action {
		ADDITION, SUBTRACTION, MULTIPLICATION, DIVISION, EQUALS, EXPONENT, COSINUS, SINUS, TANGENS, SQUARE_ROOT, ROOT, LN, LOG, POWER_OF_TWO, POWER, PERCENTAGE, NATURAL_EXPONENT, PI, MEMORY_STORE, MEMORY_RECALL, MEMORY_CLEAR
	}

	private final Button[] button = new Button[35];
	private TextView screenTxt;
	private Boolean actionPressed = false;
	private Double answer = (double) 0;
	private String storedNum;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		vibe = (Vibrator) getSystemService(VIBRATOR_SERVICE);

		screenTxt = (TextView) findViewById(R.id.displayTextView);

		button[0] = (Button) findViewById(R.id.zeroBtn);// zeroBtn
		button[1] = (Button) findViewById(R.id.oneBtn);// oneBtn
		button[2] = (Button) findViewById(R.id.twoBtn);// twoBtn
		button[3] = (Button) findViewById(R.id.threeBtn);// threeBtn
		button[4] = (Button) findViewById(R.id.fourBtn);// fourBtn
		button[5] = (Button) findViewById(R.id.fiveBtn);// fiveBtn
		button[6] = (Button) findViewById(R.id.sixBtn);// sixBtn
		button[7] = (Button) findViewById(R.id.sevenBtn);// sevenBtn
		button[8] = (Button) findViewById(R.id.eightBtn);// eightBtn
		button[9] = (Button) findViewById(R.id.nineBtn);// nineBtn

		button[10] = (Button) findViewById(R.id.logarithmBtn);// logarithmBtn
		button[11] = (Button) findViewById(R.id.powerBtn);// powerBtn
		button[12] = (Button) findViewById(R.id.xPowerTwoBtn);// xPowerTwoBtn
		button[13] = (Button) findViewById(R.id.sqrtBtn);// squareBtn
		button[14] = (Button) findViewById(R.id.rootBtn);// squareOfXBtn
		button[15] = (Button) findViewById(R.id.percentBtn);// percentBtn
		button[16] = (Button) findViewById(R.id.piBtn); // PI
		button[17] = (Button) findViewById(R.id.memoryClearBtn); // memoryClearBtn
		button[18] = (Button) findViewById(R.id.memoryRecallBtn);// memoryRecallBtn
		button[19] = (Button) findViewById(R.id.deleteBtn);// deleteBtn
		button[20] = (Button) findViewById(R.id.acBtn);// acBtn
		button[21] = (Button) findViewById(R.id.memoryStoreBtn);// memoryStoreBtn
		button[22] = (Button) findViewById(R.id.equalsBtn);// equalsBtn
		button[23] = (Button) findViewById(R.id.exponentBtn);// exponentBtn
		button[24] = (Button) findViewById(R.id.multiplicationBtn);// multiplicationBtn
		button[25] = (Button) findViewById(R.id.divisionBtn);// divisionBtn
		button[26] = (Button) findViewById(R.id.tangensBtn);// tangensBtn
		button[27] = (Button) findViewById(R.id.cosinusBtn);// cosinusBtn
		button[28] = (Button) findViewById(R.id.sinusBtn);// sinusBtn
		button[29] = (Button) findViewById(R.id.additionBtn);// additionBtn
		button[30] = (Button) findViewById(R.id.subtractionBtn);// subtractionBtn
		button[31] = (Button) findViewById(R.id.lanBtn);// lanBtn
		button[32] = (Button) findViewById(R.id.posNegBtn);// posNegBtn
		button[33] = (Button) findViewById(R.id.dotBtn);// dotBtn
		button[34] = (Button) findViewById(R.id.eBtn);// Natural exponent

		for (int i = 0; i < button.length; i++)
			button[i].setOnClickListener(buttonListener);
		clearDisplay();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;

	}

	public void updateDisplay(String strNum) {
		String text = (String) screenTxt.getText();

		if (text.getBytes().length <= DEFAULT_LENGTH) {
			if (strNum.getBytes().length <= DEFAULT_LENGTH) {
				if (text.equals("0") && !strNum.equals(".")) {
					if (strNum.equals("0"))
						clearDisplay();
					else
						screenTxt.setText(strNum);
				} else {
					screenTxt.setText(text + strNum);
				}
			}
		}
	}

	private void memoryFunctions(Action action) {
		switch (action) {
		case MEMORY_STORE:
			storedNum = (String) screenTxt.getText();
			break;
		case MEMORY_RECALL:
			if (storedNum != null) {
				clearDisplay();
				screenTxt.setText(storedNum);
			} else
				clearDisplay();
			break;
		case MEMORY_CLEAR:
			storedNum = null;
			break;
		default:
			break;
		}
	}

	public void updateDisplay(Double number) {
		try {
			String text = (String) screenTxt.getText();
			Long longNumber;
			Double prevNum = Double.parseDouble(text);

			if (number == Math.floor(number)) {
				longNumber = (long) Math.floor(number);
				if (longNumber >= Long.MAX_VALUE)
					error();
				else if (longNumber < Long.MIN_VALUE)
					error();
				else if (String.valueOf(longNumber).length() <= DEFAULT_LENGTH) {
					if (prevNum == 0) {
						if (longNumber > MAX_NUM) {
							screenTxt.setText(maxNumFormat.format(longNumber)
									.toString());
						} else if (longNumber < MIN_NUM) {
							screenTxt.setText(maxNumFormat.format(longNumber)
									.toString());
						} else
							screenTxt.setText(longNumber.toString());
					} else
						screenTxt.setText(text + longNumber.toString());

				} else if (String.valueOf(longNumber).length() > DEFAULT_LENGTH) {
					if (prevNum == 0)
						screenTxt.setText(maxNumFormat.format(longNumber)
								.toString());
					else
						screenTxt.setText(text
								+ maxNumFormat.format(longNumber).toString());
				}

			} else {
				if (number >= Double.MAX_VALUE)
					error();
				else if (String.valueOf(number).length() <= DEFAULT_LENGTH) {
					if (prevNum == 0)
						screenTxt.setText(number.toString());
					else
						screenTxt.setText(text + number.toString());

				} else if (String.valueOf(number).length() > DEFAULT_LENGTH) {
					String num = null;
					num = myFormat.format(number);
					if (num.toString().equals("-0"))
						num = "0";
					if (number >= MAX_NUM || number <= MIN_NUM) {
						screenTxt.setText(maxNumFormat.format(number)
								.toString());

					} else {
						String str1 = number.toString();
						if (str1.indexOf("E") > 0) {
							screenTxt.setText(maxNumFormat.format(number)
									.toString());
						} else {
							num = strLengthFix(number);
							screenTxt.setText(num);
						}
					}

				}
			}
		} catch (Exception e) {
			error();
			Log.d("Kryd exeption", e.getMessage());
		}
	}

	private String strLengthFix(Double number) {
		String str = number.toString();
		String strBeforeDec, strAfterDec;

		int dotIndex = str.indexOf(".");
		strBeforeDec = str.substring(0, dotIndex);
		strAfterDec = str.substring(dotIndex + 1);
		Long firstNum = Long.parseLong(strAfterDec);
		firstNum= (long) Math.round(firstNum/10);

		/*
		 * while (String.valueOf(number).length() > DEFAULT_LENGTH) {
		 * 
		 * }
		 */

		return "0";
	}

	public void clearDisplay() {
		screenTxt.setText(DEFAULT_TEXT);
		actionPressed = false;
	}

	public void init() {
		answer = (double) 0;
		actionCount = 0;
	}

	public void error() {
		clearDisplay();
		screenTxt.setText("Error");
	}

	View.OnClickListener buttonListener = new OnClickListener() {

		public void onClick(View v) {
			vibe.vibrate(40);
			Double numberFromScreen = (double) 0;

			if (screenTxt.getText() == "Error") {
				clearDisplay();
				init();
			}
			for (int i = 0; i < 10; i++) {
				if (v.getId() == button[i].getId()) {
					if (actionPressed || prevAction == Action.EQUALS) {
						clearDisplay();
						actionPressed = false;
					}
					updateDisplay(String.valueOf(i));
				}
			}
			try {
				numberFromScreen = Double.parseDouble((String) screenTxt
						.getText());
			} catch (Exception e) {
				error();
			}

			if (v.getId() == button[20].getId()) {
				clearDisplay();
				init();
			} else if (v.getId() == button[19].getId()) {
				if (screenTxt.getText().length() > 1) {
					screenTxt.setText(screenTxt.getText().toString()
							.substring(0, screenTxt.getText().length() - 1));
				} else if (screenTxt.getText().length() == 1
						&& screenTxt.getText() != "0") {
					clearDisplay();
					init();
				}
			} else if (v.getId() == button[29].getId()) {
				calculations(numberFromScreen, Action.ADDITION);
			} else if (v.getId() == button[30].getId()) {
				calculations(numberFromScreen, Action.SUBTRACTION);
			} else if (v.getId() == button[24].getId()) {
				calculations(numberFromScreen, Action.MULTIPLICATION);
			} else if (v.getId() == button[25].getId()) {
				calculations(numberFromScreen, Action.DIVISION);
			} else if (v.getId() == button[22].getId()) {
				calculations(numberFromScreen, Action.EQUALS);
			} else if (v.getId() == button[33].getId()) {
				updateDisplay(".");
			} else if (v.getId() == button[32].getId()) {
				clearDisplay();
				updateDisplay(numberFromScreen * (-1));
			} else if (v.getId() == button[12].getId()) {
				calculations(numberFromScreen, Action.POWER_OF_TWO);
			} else if (v.getId() == button[13].getId()) {
				calculations(numberFromScreen, Action.SQUARE_ROOT);
			} else if (v.getId() == button[26].getId()) {
				calculations(numberFromScreen, Action.TANGENS);
			} else if (v.getId() == button[27].getId()) {
				calculations(numberFromScreen, Action.COSINUS);
			} else if (v.getId() == button[28].getId()) {
				calculations(numberFromScreen, Action.SINUS);
			} else if (v.getId() == button[31].getId()) {
				calculations(numberFromScreen, Action.LN);
			} else if (v.getId() == button[10].getId()) {
				calculations(numberFromScreen, Action.LOG);
			} else if (v.getId() == button[11].getId()) {
				calculations(numberFromScreen, Action.POWER);
			} else if (v.getId() == button[23].getId()) {
				calculations(numberFromScreen, Action.EXPONENT);
			} else if (v.getId() == button[14].getId()) {
				calculations(numberFromScreen, Action.ROOT);
			} else if (v.getId() == button[15].getId()) {
				calculations(numberFromScreen, Action.PERCENTAGE);
			} else if (v.getId() == button[16].getId()) {
				calculations(numberFromScreen, Action.PI);
			} else if (v.getId() == button[34].getId()) {
				calculations(numberFromScreen, Action.NATURAL_EXPONENT);
			} else if (v.getId() == button[21].getId()) {
				memoryFunctions(Action.MEMORY_STORE);
			} else if (v.getId() == button[17].getId()) {
				memoryFunctions(Action.MEMORY_CLEAR);
			} else if (v.getId() == button[18].getId()) {
				memoryFunctions(Action.MEMORY_RECALL);
			}

		}
	};

	private void calculations(Double number, Action action) {

		switch (action) {
		case ADDITION:
			if (actionCount == 0) {
				prevAction = action;
				answer = number;
				actionCount++;
			} else if (actionCount > 0 && prevAction == action) {
				answer += number;
				clearDisplay();
				updateDisplay(answer);
			} else if (actionCount > 0 && prevAction != action) {
				calculations(number, prevAction);
				prevAction = action;
			}

			actionPressed = true;
			break;

		case SUBTRACTION:
			if (actionCount == 0) {
				prevAction = action;
				answer = number;
				actionCount++;
			} else if (actionCount > 0 && prevAction == action) {
				answer -= number;
				clearDisplay();
				updateDisplay(answer);
			} else if (actionCount > 0 && prevAction != action) {
				calculations(number, prevAction);
				prevAction = action;
			}

			actionPressed = true;
			break;

		case MULTIPLICATION:
			if (actionCount == 0) {
				prevAction = action;
				answer = number;
				actionCount++;
			} else if (actionCount > 0 && prevAction == action) {
				answer *= number;
				clearDisplay();
				updateDisplay(answer);
			} else if (actionCount > 0 && prevAction != action) {
				calculations(number, prevAction);
				prevAction = action;
			}

			actionPressed = true;
			break;

		case DIVISION:
			if (actionCount == 0) {
				prevAction = action;
				answer = number;
				actionCount++;
			} else if (actionCount > 0 && prevAction == action) {
				if (number != 0)
					answer /= number;
				else {
					error();
					break;
				}
				clearDisplay();
				updateDisplay(answer);
			} else if (actionCount > 0 && prevAction != action) {
				calculations(number, prevAction);
				prevAction = action;
			}

			actionPressed = true;
			break;

		case POWER_OF_TWO:
			clearDisplay();
			Double ans = (double) Math.pow(number, 2);
			updateDisplay(ans);
			break;

		case SQUARE_ROOT:
			if (number < 0) {
				error();
				break;
			} else {
				clearDisplay();
				ans = (double) Math.sqrt(number);
				updateDisplay(ans);
			}

			break;

		case TANGENS:
			clearDisplay();
			String str = myFormat.format(Math.cos(Math.toRadians(number)));
			if (str.equals("0") || str.equals("-0")) {
				error();
				break;
			}
			ans = (double) Math.tan(Math.toRadians(number));
			updateDisplay(ans);

			break;

		case COSINUS:
			clearDisplay();
			ans = Math.cos(Math.toRadians(number));
			updateDisplay(ans);

			break;

		case SINUS:
			clearDisplay();
			ans = Math.sin(Math.toRadians(number));
			updateDisplay(ans);

			break;

		case LN:
			clearDisplay();
			ans = (double) Math.log(number);
			updateDisplay(ans);

			break;

		case LOG:
			clearDisplay();
			ans = (double) Math.log10(number);
			updateDisplay(ans);

			break;

		case EQUALS:
			if (actionCount > 0) {
				calculations(number, prevAction);
				actionCount = 0;
			}
			break;

		case POWER:
			if (actionCount == 0) {
				prevAction = action;
				answer = number;
				actionCount++;
			} else if (actionCount > 0 && prevAction == action) {
				answer = Math.pow(answer, number);
				clearDisplay();
				updateDisplay(answer);
			} else if (actionCount > 0 && prevAction != action) {
				calculations(number, prevAction);
				prevAction = action;
			}

			actionPressed = true;
			break;

		case EXPONENT:
			if (actionCount == 0) {
				prevAction = action;
				answer = number;
				actionCount++;
			} else if (actionCount > 0 && prevAction == action) {
				answer *= Math.pow(10d, number);
				clearDisplay();
				updateDisplay(answer);
			} else if (actionCount > 0 && prevAction != action) {
				calculations(number, prevAction);
				prevAction = action;
			}

			actionPressed = true;
			break;

		case ROOT:
			if (actionCount == 0) {
				prevAction = action;
				answer = number;
				actionCount++;
			} else if (actionCount > 0 && prevAction == action) {
				answer = Math.pow(answer, 1 / number);
				clearDisplay();
				updateDisplay(answer);
			} else if (actionCount > 0 && prevAction != action) {
				calculations(number, prevAction);
				prevAction = action;
			}

			actionPressed = true;
			break;

		case PERCENTAGE:
			if (actionCount == 0) {
				prevAction = action;
				answer = number;
				actionCount++;
			} else if (actionCount > 0 && prevAction == action) {
				answer = (answer / 100) * number;
				clearDisplay();
				updateDisplay(answer);
			} else if (actionCount > 0 && prevAction != action) {
				calculations(number, prevAction);
				prevAction = action;
			}
			actionPressed = true;
			break;

		case PI:
			clearDisplay();
			ans = Math.PI;
			updateDisplay(ans);

			break;

		case NATURAL_EXPONENT:
			clearDisplay();
			ans = Math.E;
			updateDisplay(ans);

			break;

		default:
			break;
		}
	}
}
