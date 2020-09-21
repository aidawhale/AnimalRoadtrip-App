using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using Mirror;

public class PositionPlayers : MonoBehaviour
{
    private NetworkManagerWakaMole networkManager;

    // Start is called before the first frame update
    void Start()
    {
        networkManager = GameObject.Find("NetworkManager_WakaMole").GetComponent<NetworkManagerWakaMole>();

        if (networkManager == null) { return; }
        
        foreach (GameObject player in networkManager.GamePlayers)
        {
            Transform startPos = networkManager.GetStartPosition();

            if (startPos != null)
            {
                player.transform.position = startPos.position;
                player.transform.rotation = startPos.rotation;

                player.transform.parent = startPos; // make sure it's inside of the parent
            }
        }
    }
}
