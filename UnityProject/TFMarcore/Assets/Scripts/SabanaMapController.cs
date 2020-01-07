using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class SabanaMapController : MapController {

    private GameObject chrono;

    // Start is called before the first frame update
    void Start() {
        chrono = GameObject.Find("ChronoText");

        SetScene();
    }

    public override void SetScene() {
        chrono.SendMessage("OnInitChrono");

        // ...

        chrono.SendMessage("OnStartChrono");
    }

    public override void MyVictory() {
        BaseVictory();

        // Victory on map
        // ...

        chrono.SendMessage("OnStopChrono");
    }

    public override void RestartGame() {
        Handheld.Vibrate();

        // Reset map
        // ...

        BroadcastMessage("OnResetPosition");

        SetScene();

    }

    public void OnObjectCollision(int obj) {
        // Object can be animal or map interactable element

        if (CheckObjectOrder(obj)) {
            if (obj == 10) {
                MyVictory();
            }
            return;
        }
        RestartGame();
    }

    private bool CheckObjectOrder(int position) {

        return false;
    }

}
