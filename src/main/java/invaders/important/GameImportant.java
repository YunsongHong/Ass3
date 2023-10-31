package invaders.important;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import invaders.ConfigReader;
import invaders.create.BunkerCreater;
import invaders.create.CreateElments;
import invaders.create.EnemyCreater;
import invaders.factory.Projectile;
import invaders.gameobject.Bunker;
import invaders.gameobject.Enemy;
import invaders.gameobject.GameObject;
import invaders.entities.Player;
import invaders.rendering.Renderable;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import org.json.simple.JSONObject;

/**
 * This class manages the main loop and logic of the game
 */
public class GameImportant {
	private List<GameObject> gameObjects = new ArrayList<>(); // 每帧更新的游戏对象列表
	private List<GameObject> pendingToAddGameObject = new ArrayList<>();
	private List<GameObject> pendingToRemoveGameObject = new ArrayList<>();

	private List<Renderable> pendingToAddRenderable = new ArrayList<>();
	private List<Renderable> pendingToRemoveRenderable = new ArrayList<>();

	private List<Renderable> renderables =  new ArrayList<>();

	private Player player;
//	private GameWindow gameWindow;
	private Pane root;
	private boolean left;
	private ImageView View;
	private Rectangle space;
	private boolean right;
	private int gameWidth;
	private int gameHeight;
	private int timer = 45;
	private int sorce=0;
	private Label label;

	public GameImportant(String config){
		// 读取json配置文件
		ConfigReader.parse(config);

		// 获取游戏窗口大小配置
		gameWidth = ((Long)((JSONObject) ConfigReader.getGameInfo().get("size")).get("x")).intValue();
		gameHeight = ((Long)((JSONObject) ConfigReader.getGameInfo().get("size")).get("y")).intValue();
		System.out.println(gameWidth+":::"+gameHeight);
		//创建飞机
		this.player = new Player(ConfigReader.getPlayerInfo());
		renderables.add(player);


		CreateElments createElments = new CreateElments();
		BunkerCreater bunkerBuilder = new BunkerCreater();
		//障碍物  循环添加障碍物
		//在配置文件中我添加了4个障碍物
		for(Object eachBunkerInfo:ConfigReader.getBunkersInfo()){
			Bunker bunker = createElments.constructBunker(bunkerBuilder, (JSONObject) eachBunkerInfo);
			gameObjects.add(bunker);
			renderables.add(bunker);
		}


		EnemyCreater enemyBuilder = new EnemyCreater();
		//敌人
		//循环添加敌人，在配置文件中 我添加了2个敌人 slow_straight和fast_straight
		for(Object eachEnemyInfo:ConfigReader.getEnemiesInfo()){
			Enemy enemy = createElments.constructEnemy(this,enemyBuilder,(JSONObject)eachEnemyInfo);
			gameObjects.add(enemy);
			renderables.add(enemy);
		}

	}

	/**
	 * 更新游戏
	 */

	public void update(Label a,int value,Label scoreLabel1){
		timer+=1;//一直运行

		movePlayer();

		for(GameObject go: gameObjects){
			go.update(this);
		}
		if (!player.isAlive()){
			gameOver(scoreLabel1);
		}
		for (int i = 0; i < renderables.size(); i++) {
			Renderable renderableA = renderables.get(i);

			for (int j = i+1; j < renderables.size(); j++) {
				Renderable renderableB = renderables.get(j);
//
				if((renderableA.getRenderableObjectName().equals("Enemy") && renderableB.getRenderableObjectName().equals("EnemyProjectile"))
						||(renderableA.getRenderableObjectName().equals("EnemyProjectile") && renderableB.getRenderableObjectName().equals("Enemy"))||
						(renderableA.getRenderableObjectName().equals("EnemyProjectile") && renderableB.getRenderableObjectName().equals("EnemyProjectile"))){

//
				}else{
					if(renderableA.isColliding(renderableB) && (renderableA.getHealth()>0 && renderableB.getHealth()>0)) {
						System.out.println("这是renderableB："+renderableB.getRenderableObjectName());
						System.out.println("这是renderableA："+renderableA.getRenderableObjectName());
						//这是子弹打到对应的物体上
						renderableA.takeDamage(1);
						renderableB.takeDamage(1);
						//当player 的子弹打到 Enemy 时 那么加一分
						if (renderableB.getRenderableObjectName().equals("PlayerProjectile") && renderableA.getRenderableObjectName().equals("Enemy")){
//							增加分值
							this.sorce +=value;
							a.setText("得分: "+this.sorce);
							a.setFont(new Font("System",  20));
						}


					}


				}
			}
		}


		// ensure that renderable foreground objects don't go off-screen
		int offset = 1;
		for(Renderable ro: renderables){
			if(!ro.getLayer().equals(Renderable.Layer.FOREGROUND)){
				continue;
			}
			if(ro.getPosition().getX() + ro.getWidth() >= gameWidth) {
				ro.getPosition().setX((gameWidth - offset) -ro.getWidth());
			}

			if(ro.getPosition().getX() <= 0) {
				ro.getPosition().setX(offset);
			}

			if(ro.getPosition().getY() + ro.getHeight() >= gameHeight) {
				ro.getPosition().setY((gameHeight - offset) -ro.getHeight());
			}

			if(ro.getPosition().getY() <= 0) {
				ro.getPosition().setY(offset);
			}
		}

	}

	public List<Renderable> getRenderables(){
		return renderables;
	}

	public List<GameObject> getGameObjects() {
		return gameObjects;
	}
	public List<GameObject> getPendingToAddGameObject() {
		return pendingToAddGameObject;
	}

	public List<GameObject> getPendingToRemoveGameObject() {
		return pendingToRemoveGameObject;
	}

	public List<Renderable> getPendingToAddRenderable() {
		return pendingToAddRenderable;
	}

	public List<Renderable> getPendingToRemoveRenderable() {
		return pendingToRemoveRenderable;
	}


	public void leftReleased() {
		this.left = false;
	}

	public void rightReleased(){
		this.right = false;
	}

	public void leftPressed() {
		this.left = true;
	}
	public void rightPressed(){
		this.right = true;
	}

	public boolean shootPressed(){
		if(timer>45 && player.isAlive()){
			Projectile projectile = player.shoot();

			gameObjects.add(projectile);
			renderables.add(projectile);
			timer=0;
			return true;
		}
		return false;
	}

	private void movePlayer(){
		if(left){
			player.left();
		}

		if(right){
			player.right();
		}
	}

	public int getGameWidth() {
		return gameWidth;
	}

	public int getGameHeight() {
		return gameHeight;
	}

	public Player getPlayer() {
		return player;
	}

	/**
	 * 游戏结束后的动作
	 */
	private void gameOver(Label scoreLabel1) {
		root = new Pane();
		root.getChildren().clear();
		scoreLabel1.setText("游戏结束!");
		//设置字体大小
		scoreLabel1.setFont(new Font("System",  30));

		ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
		Runnable task = () -> {
			System.out.println("执行延迟任务");
			// 在这里进行你的处理
			Platform.exit();
			System.exit(0);
		};
		//设置5秒后自动关闭游戏游戏
		executorService.schedule(task, 3, TimeUnit.SECONDS);
	}
}
