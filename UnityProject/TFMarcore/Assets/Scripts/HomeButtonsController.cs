using System;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;
using UnityEngine.UI;

public class HomeButtonsController : MonoBehaviour {

    public GameObject homeButton;
    public GameObject cancelButton;
    public GameObject moveButton;
    public GameObject backgroundPanel;

    public GameObject chronoText;

    private GameObject map;

    public void OnHomeSelect() {
        homeButton.SetActive(false);
        cancelButton.SetActive(true);
        if(moveButton != null) {
            moveButton.SetActive(true);
        }
        backgroundPanel.SetActive(true);

        if(chronoText != null) {
            chronoText.SendMessage("OnStopChrono");
        }
    }
        

    public void OnResetGameSelect() {
        if (map == null) {
            map = GameObject.FindGameObjectWithTag("Map");
        }
        map.SendMessage("RestartGame");
        OnCancelSelect();
    }

    public void OnShowBackgroundPanel() {
        homeButton.SetActive(false);
        backgroundPanel.SetActive(true);
    }

    public void OnReloadScene() {
        SceneManager.LoadScene(SceneManager.GetActiveScene().buildIndex);
    }
    public void OnCancelSelect() {
        homeButton.SetActive(true);
        cancelButton.SetActive(false);
        if (moveButton != null) {
            moveButton.SendMessage("OnDoneSelect");
            moveButton.SetActive(false);
        }
        backgroundPanel.SetActive(false);

        if (chronoText != null) {
            chronoText.SendMessage("OnStartChrono");
        }
    }
}
