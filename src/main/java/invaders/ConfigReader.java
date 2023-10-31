package invaders;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class ConfigReader {
    private static JSONObject gameInfo;
    private static JSONObject playerInfo;
    private static JSONArray bunkersInfo;
    private static JSONArray enemiesInfo;


    public static void parse(String configPath){
        JSONParser parser = new JSONParser();
        try {
            JSONObject configObject = (JSONObject) parser.parse(new FileReader(configPath));

            // 读取游戏界面配置
            gameInfo = (JSONObject) configObject.get("Game");

	        // 读取飞机配置
            playerInfo = (JSONObject) configObject.get("Player");

			// 读取障碍物的配置
            bunkersInfo = (JSONArray) configObject.get("Bunkers");

            // 读取敌人的配置
            enemiesInfo = (JSONArray) configObject.get("Enemies");
        } catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
    }

    public static JSONObject getGameInfo() {
        return gameInfo;
    }

    public static JSONObject getPlayerInfo() {
        return playerInfo;
    }

    public static JSONArray getBunkersInfo() {
        return bunkersInfo;
    }

    public static JSONArray getEnemiesInfo() {
        return enemiesInfo;
    }
}