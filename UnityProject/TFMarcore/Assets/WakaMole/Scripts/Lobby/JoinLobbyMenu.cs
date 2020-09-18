using System.Collections;
using System.Collections.Generic;
using TMPro;
using UnityEngine;
using UnityEngine.UI;

// https://github.com/DapperDino/Mirror-Multiplayer-Tutorials/blob/master/Assets/Tutorials/Lobby/Scripts/JoinLobbyMenu.cs

public class JoinLobbyMenu : MonoBehaviour
{
    [SerializeField] private NetworkManagerWakaMole networkManager = null;

    [Header("UI")]
    [SerializeField] private GameObject landingPagePanel = null;
    [SerializeField] private TMP_InputField ipAddressInputField = null;
    [SerializeField] private Button joinButton = null;

    private void OnEnable()
    {
        NetworkManagerWakaMole.OnClientConnected += HandleClientConnected;
        NetworkManagerWakaMole.OnClientDisconnected += HandleClientDisconnected;
    }

    private void OnDisable()
    {
        NetworkManagerWakaMole.OnClientConnected -= HandleClientConnected;
        NetworkManagerWakaMole.OnClientDisconnected -= HandleClientDisconnected;
    }

    public void JoinLobby()
    {
        string ipAddress = ipAddressInputField.text;

        if (ipAddress.Trim() == "") { return; }

        networkManager.networkAddress = ipAddress;
        networkManager.StartClient();

        joinButton.interactable = false;
    }

    private void HandleClientConnected()
    {
        joinButton.interactable = true;

        gameObject.SetActive(false);
        landingPagePanel.SetActive(false);
    }

    private void HandleClientDisconnected()
    {
        joinButton.interactable = true;
    }
}
