/* **************************************************
 Copyright (c) 2012, University of Cambridge
 Neal Lathia, neal.lathia@cl.cam.ac.uk

For more information, please visit http://www.emotionsense.org

Permission to use, copy, modify, and/or distribute this software for any
purpose with or without fee is hereby granted, provided that the above
copyright notice and this permission notice appear in all copies.

THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY
SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF OR
IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 ************************************************** */

package com.ubhave.example.sensordatamanager;

import android.content.Context;

import com.ubhave.datahandler.config.DataTransferConfig;
import com.ubhave.datahandler.loggertypes.AbstractDataLogger;
import com.ubhave.example.sensordatamanager.store.ExampleStoreOnlyLogger;
import com.ubhave.sensormanager.ESException;
import com.ubhave.sensormanager.ESSensorManager;
import com.ubhave.sensormanager.SensorDataListener;
import com.ubhave.sensormanager.data.SensorData;

public class ExampleSensorListener implements SensorDataListener
{
	private ESSensorManager sensorManager;
	private AbstractDataLogger dataLogger;
	private int subscriptionId, sensorType;
	
	public ExampleSensorListener(final Context context, final int dataTransferPolicy, final int sensorType)
	{
		this.sensorType = sensorType;
		try
		{
			sensorManager = ESSensorManager.getSensorManager(context);
			if (dataTransferPolicy == DataTransferConfig.STORE_ONLY)
			{
				dataLogger = new ExampleStoreOnlyLogger(context);
			}
		}
		catch (ESException e)
		{
			sensorManager = null;
			e.printStackTrace();
		}
	}
	
	public boolean startSensing()
	{
		try
		{
			subscriptionId = sensorManager.subscribeToSensorData(sensorType, this);
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean stopSensing()
	{
		try
		{
			sensorManager.unsubscribeFromSensorData(subscriptionId);
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public void onDataSensed(SensorData data)
	{
		dataLogger.logSensorData(data);
	}

	@Override
	public void onCrossingLowBatteryThreshold(boolean isBelowThreshold)
	{
		// Nothing for example app
	}
}
