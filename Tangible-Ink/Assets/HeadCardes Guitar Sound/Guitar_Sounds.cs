using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Guitar_Sounds : MonoBehaviour {


  

    public AudioSource[] audioList = new AudioSource[6];
    

	public void PlaySound01()
    {
        audioList[0].Play();
        
    }
    public void PlaySound02()
    {
        audioList[1].Play();
      
    }
    public void PlaySound03()
    {
        audioList[2].Play();
        
    }
    public void PlaySound04()
    {
        audioList[3].Play();
        
    }
    public void PlaySound05()
    {
        audioList[4].Play();
        
    }
    public void PlaySound06()
    {
        audioList[5].Play();
       
    }

    

}
