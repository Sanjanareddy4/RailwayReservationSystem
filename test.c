
//Monday->0 Sunday->6
#include <stdio.h>
#include <stdlib.h>

int main()
{
   int num;
   FILE *fptr;

   // use appropriate location if you are using MacOS or Linux
   fptr = fopen("example.txt","w");

   if(fptr == NULL)
   {
      printf("Error!");   
      exit(1);             
   }

  
   char station1[14][50]={"Una Himachal","Nangal Dam","Anandpur Sahib","Kiratpur Sahib","Rupnagar","Sahibzada Asngr","Chandigarh","Ambala Cant Junction","Kurukshetra Junction","Karnal","Panipat Junction","Sonipat","Subzi Mandi","New Delhi"};
   char departure1[14][9]={"05:00:00","05:27:00","05:46:00","05:58:00","06:28:00","07:17:00","07:38:00","08:45:00","09:21:00","09:48:00","10:18:00","10:53:00","11:32:00","12:00:00"};
   char arrival1[14][9]=  {"05:00:00","05:24:00","05:44:00","05:56:00","06:26:00","07:15:00","07:30:00","08:30:00","09:19:00","09:46:00","10:16:00","10:51:00","11:30:00","12:00:00"};
    for(int i=0;i<13;i++)
    {
        for(int j=i+1;j<14;j++)
        {
            for(int z=0;z<7;z++)
            {
               fprintf(fptr,"INSERT INTO ROUTES(SOURCE,DESTINATION,DEPT_TIME,ARRIVAL_TIME,DAY_REACH,DAY_DEPARTURE,TRAIN_NO)VALUES('%s','%s','%s','%s',%d,%d,12058);\n",station1[i],station1[j],departure1[i],arrival1[j],z,z);
            }
        }
    }

   char station2[12][50]={"Ajmer Junction","Beawer","Marwar Junction","Falna","Sirohi Road","Abu Road","Palanpur Junction","Siddhpur","Unjha","Mahesana Junction","Sabarmati Junction","Ahmedabad Junction"};
   char departure2[12][9]={"06:25:00","07:11:00","08:21:00","09:16:00","10:20:00","11:17:00","12:50:00","13:20:00","13:38:00","14:00:00","14:53:00","15:35:00"};
   char arrival2[12][9]=  {"06:25:00","07:09:00","08:15:00","09:14:00","10:18:00","11:07:00","12:48:00","13:18:00","13:36:00","13:58:00","14:51:00","15:35:00"};
    for(int i=0;i<11;i++)
    {
        for(int j=i+1;j<12;j++)
        {
            for(int z=0;z<7;z++)
            {
               fprintf(fptr,"INSERT INTO ROUTES(SOURCE,DESTINATION,DEPT_TIME,ARRIVAL_TIME,DAY_REACH,DAY_DEPARTURE,TRAIN_NO)VALUES('%s','%s','%s','%s',%d,%d,19412);\n",station2[i],station2[j],departure2[i],arrival2[j],z,z);
            }
        }
    }

   char station3[15][50]={"Jaipur","Phulera Junction","Kishangarh","Ajmer Junction","Nasirabad","Bijainagar","Gulabpura","Mandal","Bhilwara","Chittaurgarh","Kapasan","Fatehnagar","Mavli Junction","Ranapratapnagar","Udaipur City"};
   char departure3[15][9]={"14:00:00","14:42:00","15:21:00","16:10:00","16:34:00","17:06:00","17:14:00","17:50:00","18:04:00","19:25:00","19:58:00","20:19:00","20:34:00","21:11:00","21:30:00"};
   char arrival3[15][9]=  {"14:00:00","14:40:00","15:19:00","16:05:00","16:32:00","17:04:00","17:12:00","17:48:00","18:01:00","19:05:00","19:56:00","20:17:00","20:32:00","21:09:00","21:30:00"};
    for(int i=0;i<14;i++)
    {
        for(int j=i+1;j<15;j++)
        {
            for(int z=0;z<7;z++)
            {
               fprintf(fptr,"INSERT INTO ROUTES(SOURCE,DESTINATION,DEPT_TIME,ARRIVAL_TIME,DAY_REACH,DAY_DEPARTURE,TRAIN_NO)VALUES('%s','%s','%s','%s',%d,%d,12992);\n",station3[i],station3[j],departure3[i],arrival3[j],z,z);
            }
        }
    }

   char station4[8][50]={"New Delhi","Jhansi Junction","Bhopal Junction","Nagpur","Kacheguda","Kurnool Town","Dharmavaram Junction","Yesvantpur Junction"};
   char departure4[8][9]={"06:40:00","12:15:00","16:15:00","22:20:00","07:15:00","11:07:00","15:20:00","19:35:00"};
   char arrival4[8][9]=  {"06:40:00","12:05:00","16:05:00","22:10:00","07:05:00","11:05:00","15:15:00","19:35:00"};
    for(int i=0;i<3;i++)
    {
        for(int j=i+1;j<4;j++)
        {
            for(int z=0;z<7;z++)
            {
                if(z==0||z==1||z==3||z==5||z==6)
                    fprintf(fptr,"INSERT INTO ROUTES(SOURCE,DESTINATION,DEPT_TIME,ARRIVAL_TIME,DAY_REACH,DAY_DEPARTURE,TRAIN_NO)VALUES('%s','%s','%s','%s',%d,%d,12650);\n",station4[i],station4[j],departure4[i],arrival4[j],z,z);
            }
        }
    }

    for(int i=4;i<7;i++)
    {
        for(int j=i+1;j<8;j++)
        {
            for(int z=0;z<7;z++)
            {
                if(z==1||z==2||z==4||z==6||z==0)
                    fprintf(fptr,"INSERT INTO ROUTES(SOURCE,DESTINATION,DEPT_TIME,ARRIVAL_TIME,DAY_REACH,DAY_DEPARTURE,TRAIN_NO)VALUES('%s','%s','%s','%s',%d,%d,12650);\n",station4[i],station4[j],departure4[i],arrival4[j],z,z);
            }
        }
    }

    for(int i=0;i<4;i++)
    {
        for(int j=4;j<8;j++)
        {
            for(int z=0;z<7;z++)
            {
                if(z==0||z==1||z==3||z==5||z==6)
                    fprintf(fptr,"INSERT INTO ROUTES(SOURCE,DESTINATION,DEPT_TIME,ARRIVAL_TIME,DAY_REACH,DAY_DEPARTURE,TRAIN_NO)VALUES('%s','%s','%s','%s',%d,%d,12650);\n",station4[i],station4[j],departure4[i],arrival4[j],z,(z+1)%7);
            }
        }
    }

   char station5[12][50]={"New Delhi","Jhansi Junction","Bhopal Junction","Nagpur","Kazipet Junction","Secunderabad Junction","Seram","Raichur","Anantapur","Dharmavaram Junction","Sai P Nilayam","Bangalore Cy Junction"};
   char departure5[12][9]={"20:45:00","01:20:00","04:40:00","10:25:00","16:07:00","18:50:00","21:00:00","23:10:00","02:20:00","03:30:00","04:05:00","06:40:00"};
   char arrival5[12][9]=  {"20:45:00","01:15:00","04:30:00","10:15:00","16:05:00","18:35:00","20:58:00","23:08:00","02:18:00","03:25:00","04:03:00","06:40:00"};
    
    for(int i=1;i<7;i++)
    {
        for(int j=i+1;j<8;j++)
        {
            for(int z=0;z<7;z++)
            {
                if(z==1||z==2||z==5||z==6)
                    fprintf(fptr,"INSERT INTO ROUTES(SOURCE,DESTINATION,DEPT_TIME,ARRIVAL_TIME,DAY_REACH,DAY_DEPARTURE,TRAIN_NO)VALUES('%s','%s','%s','%s',%d,%d,22692);\n",station5[i],station5[j],departure5[i],arrival5[j],z,z);
            }
        }
    }

    for(int i=8;i<11;i++)
    {
        for(int j=i+1;j<12;j++)
        {
            for(int z=0;z<7;z++)
            {
                if(z==2||z==3||z==6||z==0)
                    fprintf(fptr,"INSERT INTO ROUTES(SOURCE,DESTINATION,DEPT_TIME,ARRIVAL_TIME,DAY_REACH,DAY_DEPARTURE,TRAIN_NO)VALUES('%s','%s','%s','%s',%d,%d,22692);\n",station5[i],station5[j],departure5[i],arrival5[j],z,z);
            }
        }
    }

    for(int i=0;i<1;i++)
    {
        for(int j=1;j<8;j++)
        {
            for(int z=0;z<7;z++)
            {
                if(z==0||z==1||z==4||z==5)
                    fprintf(fptr,"INSERT INTO ROUTES(SOURCE,DESTINATION,DEPT_TIME,ARRIVAL_TIME,DAY_REACH,DAY_DEPARTURE,TRAIN_NO)VALUES('%s','%s','%s','%s',%d,%d,22692);\n",station5[i],station5[j],departure5[i],arrival5[j],z,(z+1)%7);
            }
        }
    }

    for(int i=0;i<1;i++)
    {
        for(int j=8;j<12;j++)
        {
            for(int z=0;z<7;z++)
            {
                if(z==0||z==1||z==4||z==5)
                    fprintf(fptr,"INSERT INTO ROUTES(SOURCE,DESTINATION,DEPT_TIME,ARRIVAL_TIME,DAY_REACH,DAY_DEPARTURE,TRAIN_NO)VALUES('%s','%s','%s','%s',%d,%d,22692);\n",station5[i],station5[j],departure5[i],arrival5[j],z,(z+2)%7);
            }
        }
    }

    for(int i=1;i<8;i++)
    {
        for(int j=8;j<12;j++)
        {
            for(int z=0;z<7;z++)
            {
                if(z==0||z==1||z==4||z==5)
                    fprintf(fptr,"INSERT INTO ROUTES(SOURCE,DESTINATION,DEPT_TIME,ARRIVAL_TIME,DAY_REACH,DAY_DEPARTURE,TRAIN_NO)VALUES('%s','%s','%s','%s',%d,%d,22692);\n",station5[i],station5[j],departure5[i],arrival5[j],(z+1)%7,(z+2)%7);
            }
        }
    }

   char station6[3][50]={"Chandigarh","Ambala Cant Junction","New Delhi"};
   char departure6[3][9]={"12:00:00","12:42:00","15:20:00"};
   char arrival6[3][9]=  {"12:00:00","12:40:00","15:20:00"};
    for(int i=0;i<2;i++)
    {
        for(int j=i+1;j<3;j++)
        {
            for(int z=0;z<6;z++)
            {
               fprintf(fptr,"INSERT INTO ROUTES(SOURCE,DESTINATION,DEPT_TIME,ARRIVAL_TIME,DAY_REACH,DAY_DEPARTURE,TRAIN_NO)VALUES('%s','%s','%s','%s',%d,%d,12046);\n",station6[i],station6[j],departure6[i],arrival6[j],z,z);
            }
        }
    }

   char station7[6][50]={"Kalka","Chandigarh","Ambala Cant Junction","Kurukshetra Junction","Panipat Junction","New Delhi"};
   char departure7[6][9]={"17:45:00","18:23:00","19:05:00","19:40:00","20:32:00","21:55:00"};
   char arrival7[6][9]=  {"17:45:00","18:15:00","19:03:00","19:38:00","20:30:00","21:55:00"};
    for(int i=0;i<5;i++)
    {
        for(int j=i+1;j<6;j++)
        {
            for(int z=0;z<7;z++)
            {
               fprintf(fptr,"INSERT INTO ROUTES(SOURCE,DESTINATION,DEPT_TIME,ARRIVAL_TIME,DAY_REACH,DAY_DEPARTURE,TRAIN_NO)VALUES('%s','%s','%s','%s',%d,%d,12012);\n",station7[i],station7[j],departure7[i],arrival7[j],z,z);
            }
        }
    }

   return 0;
}
