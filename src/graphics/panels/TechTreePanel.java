package graphics.panels;

import Processing.Player.Player;
import graphics.components.button.ButtonComponent;
import graphics.components.tiledmap.CityComponent;
import graphics.components.tiledmap.GameMapComponent;
import org.newdawn.slick.*;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;


public class TechTreePanel extends Panel implements ComponentListener {

    private Player player;
    private ButtonComponent exitButton;

    private boolean exit = false;

    private Image imageProcess;
    private Image imageProcessing;
    private ButtonComponent[] researchTech = new ButtonComponent[6];

    private ButtonComponent[] engineeringTechButton = new ButtonComponent[2];
    private ButtonComponent[] societyTechButton = new ButtonComponent[2];
    private ButtonComponent[] arcanumTechButton = new ButtonComponent[2];

    public TechTreePanel(GameContainer gameContainer, Player player) {
        super(360, 420, 1200, 240, 0);
        this.player = player;
        try {
            this.exitButton = new ButtonComponent(gameContainer, new Image("assets/graphics/buttons/unit_control/colonise.png"),1540, 410, 30, 30);
            this.exitButton.addListener(this);
            this.imageProcess = new Image("assets/graphics/buttons/process.png");
            this.imageProcessing = new Image("assets/graphics/buttons/processing.png");
            //researchTech[0] = new ButtonComponent(gameContainer, new Image("assets/graphics/buttons/unit_control/colonise.png"), 755, 475, 50, 50);
            //researchTech[1] = new ButtonComponent(gameContainer, new Image("assets/graphics/buttons/unit_control/colonise.png"), 755, 535, 50, 50);
            //researchTech[2] = new ButtonComponent(gameContainer, new Image("assets/graphics/buttons/unit_control/colonise.png"), 1055, 475, 50, 50);
            //researchTech[3] = new ButtonComponent(gameContainer, new Image("assets/graphics/buttons/unit_control/colonise.png"), 1055, 535, 50, 50);
            //researchTech[4] = new ButtonComponent(gameContainer, new Image("assets/graphics/buttons/unit_control/colonise.png"), 1355, 475, 50, 50);
            //researchTech[5] = new ButtonComponent(gameContainer, new Image("assets/graphics/buttons/unit_control/colonise.png"), 1355, 535, 50, 50);

            //for(int i = 0; i < researchTech.length; i++) {
            //    researchTech[i].addListener(this);
            //}
            engineeringTechButton[0] = new ButtonComponent(gameContainer, this.imageProcess, 665, 460, 90, 60);
            engineeringTechButton[1] = new ButtonComponent(gameContainer, this.imageProcess, 665, 560, 90, 60);
            societyTechButton[0] = new ButtonComponent(gameContainer, this.imageProcess, 1065, 460, 90, 60);
            societyTechButton[1] = new ButtonComponent(gameContainer, this.imageProcess, 1065, 560, 90, 60);
            arcanumTechButton[0] = new ButtonComponent(gameContainer, this.imageProcess, 1465, 460, 90, 60);
            arcanumTechButton[1] = new ButtonComponent(gameContainer, this.imageProcess, 1465, 560, 90, 60);

            engineeringTechButton[0].addListener(this);
            engineeringTechButton[1].addListener(this);
            societyTechButton[0].addListener(this);
            societyTechButton[1].addListener(this);
            arcanumTechButton[0].addListener(this);
            arcanumTechButton[1].addListener(this);
        } catch (SlickException e) {
            e.printStackTrace();
        }
        init();
    }

    public void init() {
        this.exit = false;
        update();
    }

    @Override
    public void mouseMovedSignalise(int oldx, int oldy, int newx, int newy) {
        super.mouseMovedSignalise(oldx, oldy, newx, newy);
        engineeringTechButton[0].mouseMovedSignalise(oldx, oldy, newx, newy);
        engineeringTechButton[1].mouseMovedSignalise(oldx, oldy, newx, newy);
        societyTechButton[0].mouseMovedSignalise(oldx, oldy, newx, newy);
        societyTechButton[1].mouseMovedSignalise(oldx, oldy, newx, newy);
        arcanumTechButton[0].mouseMovedSignalise(oldx, oldy, newx, newy);
        arcanumTechButton[1].mouseMovedSignalise(oldx, oldy, newx, newy);
        exitButton.mouseMovedSignalise(oldx, oldy, newx, newy);
    }

    @Override
    public void mousePressedSignalise(int button, int x, int y) {
        super.mousePressedSignalise(button, x, y);
        engineeringTechButton[0].mousePressedSignalise(button, x, y);
        engineeringTechButton[1].mousePressedSignalise(button, x, y);
        societyTechButton[0].mousePressedSignalise(button, x, y);
        societyTechButton[1].mousePressedSignalise(button, x, y);
        arcanumTechButton[0].mousePressedSignalise(button, x, y);
        arcanumTechButton[1].mousePressedSignalise(button, x, y);
        exitButton.mousePressedSignalise(button, x, y);
    }

    public void update() {
        engineeringTechButton[0].setImage(this.imageProcess);
        engineeringTechButton[1].setImage(this.imageProcess);
        societyTechButton[0].setImage(this.imageProcess);
        societyTechButton[1].setImage(this.imageProcess);
        arcanumTechButton[0].setImage(this.imageProcess);
        arcanumTechButton[1].setImage(this.imageProcess);

        engineeringTechButton[0].setLocked(true);
        engineeringTechButton[1].setLocked(true);
        societyTechButton[0].setLocked(true);
        societyTechButton[1].setLocked(true);
        arcanumTechButton[0].setLocked(true);
        arcanumTechButton[1].setLocked(true);
        if(this.player.toChooseEngineering != null){
            if(this.player.toChooseEngineering.size() >= 1) {
                this.engineeringTechButton[0].setLocked(false);
                if(this.player.chosenEngineering != null && this.player.chosenEngineering == this.player.toChooseEngineering.get(0)) {
                    this.engineeringTechButton[0].setImage(this.imageProcessing);
                    this.engineeringTechButton[0].setLocked(true);
                }
            }
            if(this.player.toChooseEngineering.size() == 2) {
                this.engineeringTechButton[1].setLocked(false);
                if(this.player.chosenEngineering != null && this.player.chosenEngineering == this.player.toChooseEngineering.get(1)) {
                    this.engineeringTechButton[1].setImage(this.imageProcessing);
                    this.engineeringTechButton[1].setLocked(true);
                }
            }
        }
        if(this.player.toChooseSociety != null){
            if(this.player.toChooseSociety.size() >= 1) {
                this.societyTechButton[0].setLocked(false);
                if(this.player.chosenSociety != null && this.player.chosenSociety == this.player.toChooseSociety.get(0)) {
                    this.societyTechButton[0].setImage(this.imageProcessing);
                    this.societyTechButton[0].setLocked(true);
                }
            }
            if(this.player.toChooseSociety.size() == 2) {
                this.societyTechButton[1].setLocked(false);
                if(this.player.chosenSociety != null && this.player.chosenSociety == this.player.toChooseSociety.get(1)) {
                    this.societyTechButton[1].setImage(this.imageProcessing);
                    this.societyTechButton[1].setLocked(true);
                }
            }
        }
        if(this.player.toChooseArcanum != null){
            if(this.player.toChooseArcanum.size() >= 1) {
                this.arcanumTechButton[0].setLocked(false);
                if(this.player.chosenArcanum != null && this.player.chosenArcanum == this.player.toChooseArcanum.get(0)) {
                    this.arcanumTechButton[0].setImage(this.imageProcessing);
                    this.arcanumTechButton[0].setLocked(true);
                }
            }
            if(this.player.toChooseArcanum.size() == 2) {
                this.arcanumTechButton[1].setLocked(false);
                if(this.player.chosenArcanum != null && this.player.chosenArcanum == this.player.toChooseArcanum.get(1)) {
                    this.arcanumTechButton[1].setImage(this.imageProcessing);
                    this.arcanumTechButton[1].setLocked(true);
                }
            }
        }
    }

    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {
        g.setColor(Color.black);
        g.fillRect(this.x-10, this.y-10, this.width+20, this.height+20);
        g.setColor(Color.white);
        g.drawString("Tech tree", this.x, this.y);
        g.drawString("Engineering science", this.x, this.y+20);
        g.drawString("Society science", this.x+400, this.y+20);
        g.drawString("Arcanum science", this.x+800, this.y+20);

        if(this.player.toChooseEngineering != null){
            if(this.player.toChooseEngineering.size() >= 1) {
                this.engineeringTechButton[0].render(container, g);
                g.setColor(Color.white);
                g.drawString(this.player.toChooseEngineering.get(0).toString(), this.x, this.y + 40);
                if(this.player.toChooseEngineering.get(0).techGiven.size() >= 1) {
                    g.drawString(this.player.toChooseEngineering.get(0).techGiven.get(0).toString(), this.x+50, this.y + 60);
                }
                if(this.player.toChooseEngineering.get(0).techGiven.size() == 2) {
                    g.drawString(this.player.toChooseEngineering.get(0).techGiven.get(1).toString(), this.x+50, this.y + 80);
                }
                g.setColor(Color.gray);
                g.fillRoundRect(this.x+5, this.y + 105, 390, 30, 5);
                g.setColor(Color.green);
                g.fillRoundRect(this.x+5, this.y + 105, (float) (390 / this.player.toChooseEngineering.get(0).researchPointCost * this.player.toChooseEngineering.get(0).accumulatedResearch), 30, 5);
                g.setColor(Color.white);
                g.drawString(this.player.toChooseEngineering.get(0).accumulatedResearch + "/" + this.player.toChooseEngineering.get(0).researchPointCost, this.x+155, this.y + 111);
            }
            if(this.player.toChooseEngineering.size() == 2) {
                this.engineeringTechButton[1].render(container, g);
                g.setColor(Color.white);
                g.drawString(this.player.toChooseEngineering.get(1).toString(), this.x, this.y + 140);
                if(this.player.toChooseEngineering.get(1).techGiven.size() >= 1) {
                    g.drawString(this.player.toChooseEngineering.get(1).techGiven.get(0).toString(), this.x+50, this.y + 160);
                }
                if(this.player.toChooseEngineering.get(1).techGiven.size() == 2) {
                    g.drawString(this.player.toChooseEngineering.get(1).techGiven.get(1).toString(), this.x+50, this.y + 180);
                }
                g.setColor(Color.gray);
                g.fillRoundRect(this.x+5, this.y + 205, 390, 30, 5);
                g.setColor(Color.green);
                g.fillRoundRect(this.x+5, this.y + 205, (float) (390 / this.player.toChooseEngineering.get(1).researchPointCost * this.player.toChooseEngineering.get(1).accumulatedResearch), 30, 5);
                g.setColor(Color.white);
                g.drawString(this.player.toChooseEngineering.get(1).accumulatedResearch + "/" + this.player.toChooseEngineering.get(1).researchPointCost, this.x+155, this.y + 211);
            }
        }
        if(this.player.toChooseSociety != null){
            if(this.player.toChooseSociety.size() >= 1) {
                this.societyTechButton[0].render(container, g);
                g.setColor(Color.white);
                g.drawString(this.player.toChooseSociety.get(0).toString(), this.x + 400, this.y + 40);
                if(this.player.toChooseSociety.get(0).techGiven.size() >= 1) {
                    g.drawString(this.player.toChooseSociety.get(0).techGiven.get(0).toString(), this.x+450, this.y + 60);
                }
                if(this.player.toChooseSociety.get(0).techGiven.size() == 2) {
                    g.drawString(this.player.toChooseSociety.get(0).techGiven.get(1).toString(), this.x+450, this.y + 80);
                }
                g.setColor(Color.gray);
                g.fillRoundRect(this.x+405, this.y + 105, 390, 30, 5);
                g.setColor(Color.green);
                g.fillRoundRect(this.x+405, this.y + 105, (float) (390 / this.player.toChooseSociety.get(0).researchPointCost * this.player.toChooseSociety.get(0).accumulatedResearch), 30, 5);
                g.setColor(Color.white);
                g.drawString(this.player.toChooseSociety.get(0).accumulatedResearch + "/" + this.player.toChooseSociety.get(0).researchPointCost, this.x+555, this.y + 111);
            }
            if(this.player.toChooseSociety.size() == 2) {
                this.societyTechButton[1].render(container, g);
                g.setColor(Color.white);
                g.drawString(this.player.toChooseSociety.get(1).toString(), this.x + 400, this.y + 140);
                if(this.player.toChooseSociety.get(1).techGiven.size() >= 1) {
                    g.drawString(this.player.toChooseSociety.get(1).techGiven.get(0).toString(), this.x+450, this.y + 160);
                }
                if(this.player.toChooseSociety.get(1).techGiven.size() == 2) {
                    g.drawString(this.player.toChooseSociety.get(1).techGiven.get(1).toString(), this.x+450, this.y + 180);
                }
                g.setColor(Color.gray);
                g.fillRoundRect(this.x+405, this.y + 205, 390, 30, 5);
                g.setColor(Color.green);
                g.fillRoundRect(this.x+405, this.y + 205, (float) (390 / this.player.toChooseSociety.get(1).researchPointCost * this.player.toChooseSociety.get(1).accumulatedResearch), 30, 5);
                g.setColor(Color.white);
                g.drawString(this.player.toChooseSociety.get(1).accumulatedResearch + "/" + this.player.toChooseSociety.get(1).researchPointCost, this.x+555, this.y + 211);
            }
        }
        if(this.player.toChooseArcanum != null){
            if(this.player.toChooseArcanum.size() >= 1) {
                this.arcanumTechButton[0].render(container, g);
                g.setColor(Color.white);
                g.drawString(this.player.toChooseArcanum.get(0).toString(), this.x + 800, this.y + 40);
                if(this.player.toChooseArcanum.get(0).techGiven.size() >= 1) {
                    g.drawString(this.player.toChooseArcanum.get(0).techGiven.get(0).toString(), this.x+850, this.y + 60);
                }
                if(this.player.toChooseArcanum.get(0).techGiven.size() == 2) {
                    g.drawString(this.player.toChooseArcanum.get(0).techGiven.get(1).toString(), this.x+850, this.y + 80);
                }
                g.setColor(Color.gray);
                g.fillRoundRect(this.x+805, this.y + 105, 390, 30, 5);
                g.setColor(Color.green);
                g.fillRoundRect(this.x+805, this.y + 105, (float) (390 / this.player.toChooseArcanum.get(0).researchPointCost * this.player.toChooseArcanum.get(0).accumulatedResearch), 30, 5);
                g.setColor(Color.white);
                g.drawString(this.player.toChooseArcanum.get(0).accumulatedResearch + "/" + this.player.toChooseArcanum.get(0).researchPointCost, this.x+955, this.y + 111);
            }
            if(this.player.toChooseArcanum.size() == 2) {
                this.arcanumTechButton[1].render(container, g);
                g.setColor(Color.white);
                g.drawString(this.player.toChooseArcanum.get(1).toString(), this.x + 800, this.y + 140);
                if(this.player.toChooseArcanum.get(1).techGiven.size() >= 1) {
                    g.drawString(this.player.toChooseArcanum.get(1).techGiven.get(0).toString(), this.x+850, this.y + 160);
                }
                if(this.player.toChooseArcanum.get(1).techGiven.size() == 2) {
                    g.drawString(this.player.toChooseArcanum.get(1).techGiven.get(1).toString(), this.x+850, this.y + 180);
                }
                g.setColor(Color.gray);
                g.fillRoundRect(this.x+805, this.y + 205, 390, 30, 5);
                g.setColor(Color.green);
                g.fillRoundRect(this.x+805, this.y + 205, (float) (390 / this.player.toChooseArcanum.get(1).researchPointCost * this.player.toChooseArcanum.get(1).accumulatedResearch), 30, 5);
                g.setColor(Color.white);
                g.drawString(this.player.toChooseArcanum.get(1).accumulatedResearch + "/" + this.player.toChooseArcanum.get(1).researchPointCost, this.x+955, this.y + 211);
            }
        }
        this.exitButton.render(container, g);
    }

    @Override
    public void componentActivated(AbstractComponent abstractComponent) {
        System.out.println(2);
        if(abstractComponent == engineeringTechButton[0]) {
            this.player.chosenEngineering = this.player.toChooseEngineering.get(0);
        }
        if(abstractComponent == engineeringTechButton[1]) {
            this.player.chosenEngineering = this.player.toChooseEngineering.get(1);
        }
        if(abstractComponent == societyTechButton[0]) {
            this.player.chosenSociety = this.player.toChooseSociety.get(0);
        }
        if(abstractComponent == societyTechButton[1]) {
            this.player.chosenSociety = this.player.toChooseSociety.get(1);
        }
        if(abstractComponent == arcanumTechButton[0]) {
            this.player.chosenArcanum = this.player.toChooseArcanum.get(0);
        }
        if(abstractComponent == arcanumTechButton[1]) {
            this.player.chosenArcanum = this.player.toChooseArcanum.get(1);
        }
        if(abstractComponent == exitButton) {
            setExit(true);
        }
        update();
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
        init();
    }

    public boolean isExit() {
        return exit;
    }

    public void setExit(boolean exit) {
        this.exit = exit;
    }
}
