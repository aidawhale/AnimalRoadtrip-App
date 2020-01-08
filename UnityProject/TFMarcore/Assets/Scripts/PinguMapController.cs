using System;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class PinguMapController : MonoBehaviour {

    public GameObject Pingu;
    public GameObject InitIsland;
    public Material hatColor1;
    public Material hatColor2;

    public GameObject[] PinguFriends;

    public GameObject Campfire;
    private ParticleSystem fire;

    private int[] shapeOrder;
    private int pinguShapeNumber;

    private GameObject chrono;


    // Start is called before the first frame update
    void Start() {
        shapeOrder = new int[] { 0, 0, 0, 0, 0 , 10};
        pinguShapeNumber = -1;
        fire = Campfire.GetComponentInChildren<ParticleSystem>();
        chrono = GameObject.Find("ChronoText");
        SetScene();
    }

    private void SetScene() {
        // Random init values
        int fireplace = UnityEngine.Random.Range(0, 2); // Rand 0, 1
        int numPenguins = UnityEngine.Random.Range(1, 4); // Rand 1, 2, 3
        int hatColor = UnityEngine.Random.Range(0, 2); // Rand 0, 1

        // Init chrono
        chrono.SendMessage("OnInitChrono");

        shapeOrder[0] = numPenguins;
        if (numPenguins == 1) {
            PinguFriends[1].SetActive(false);
            PinguFriends[2].SetActive(false);
            shapeOrder[3] = 2;
            shapeOrder[4] = 3;
        }
        else if (numPenguins == 2) {
            PinguFriends[2].SetActive(false);
            shapeOrder[3] = 1;
            shapeOrder[4] = 3;
        }
        else if (numPenguins == 3) {
            PinguFriends[0].SetActive(true);
            PinguFriends[1].SetActive(true);
            PinguFriends[2].SetActive(true);
            shapeOrder[3] = 1;
            shapeOrder[4] = 2;
        }

        if (hatColor == 0) {
            // Mat 1: Green
            Pingu.SendMessage("OnChangeHatMaterial", hatColor1);
            shapeOrder[1] = 4;
            shapeOrder[2] = 5;
        }
        else {
            // Mat 2: Yellow
            Pingu.SendMessage("OnChangeHatMaterial", hatColor2);
            shapeOrder[1] = 5;
            shapeOrder[2] = 4;
        }

        if (fireplace == 0) {
            // Fire off
            if (fire != null && fire.isEmitting) {
                fire.Stop();
            }
        }
        else {
            // Fire on
            if (fire != null && !fire.isEmitting) {
                fire.Play();
            }
            if (numPenguins == 1) {
                shapeOrder[3] = 3;
                shapeOrder[4] = 2;
            }
            else if (numPenguins == 2) {
                shapeOrder[3] = 3;
                shapeOrder[4] = 1;
            }
            else if (numPenguins == 3) {
                shapeOrder[3] = 2;
                shapeOrder[4] = 1;
            }
        }

        chrono.SendMessage("OnStartChrono");

    }

    public void OnShapeCollision(int shape) {
        if(CheckShapeOrder(shape)) {
            if(shape == 10) {
                Victory();
            }
            return;
        }
        RestartGame();
    }

    private void Victory() {
        GameObject.Find("Igloo").SendMessage("OnPlayConfetti");
        GameObject.Find("HomePanel").SendMessage("OnShowBackgroundPanel");
        chrono.SendMessage("OnStopChrono");
        StartCoroutine(VictoryVibrate());
    }

    private IEnumerator VictoryVibrate() {
        yield return new WaitForSeconds(0.5f);
        Handheld.Vibrate();
        yield return new WaitForSeconds(0.5f);
        Handheld.Vibrate();
        yield return new WaitForSeconds(0.5f);
        Handheld.Vibrate();
        Handheld.Vibrate();
        Handheld.Vibrate();
    }

    private void RestartGame() {
        Handheld.Vibrate();

        // Reset counters
        pinguShapeNumber = -1;

        // Reset pingu position
        Pingu.transform.position = InitIsland.transform.position;

        SetScene();
    }

    private bool CheckShapeOrder(int position) {

        pinguShapeNumber++;

        return shapeOrder[pinguShapeNumber] == position;
    }
}
