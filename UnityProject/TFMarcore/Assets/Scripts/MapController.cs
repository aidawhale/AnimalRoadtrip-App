using System;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public abstract class MapController : MonoBehaviour {

    public abstract void SetScene();
    public abstract void MyVictory();
    public abstract void RestartGame();
    public abstract void OnUpdateItemPosition();

    public void BaseVictory() {
        StartCoroutine(VictoryVibrate());
        MyVictory();
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

}
