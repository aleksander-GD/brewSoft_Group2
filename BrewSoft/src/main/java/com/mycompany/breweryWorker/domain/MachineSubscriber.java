/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.breweryWorker.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.BiConsumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.milo.opcua.sdk.client.api.subscriptions.UaMonitoredItem;
import org.eclipse.milo.opcua.sdk.client.api.subscriptions.UaSubscription;
import org.eclipse.milo.opcua.stack.core.AttributeId;
import org.eclipse.milo.opcua.stack.core.types.builtin.ByteString;
import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.UInteger;
import org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.UShort;
import org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.Unsigned;
import org.eclipse.milo.opcua.stack.core.types.enumerated.MonitoringMode;
import org.eclipse.milo.opcua.stack.core.types.enumerated.TimestampsToReturn;
import org.eclipse.milo.opcua.stack.core.types.structured.MonitoredItemCreateRequest;
import org.eclipse.milo.opcua.stack.core.types.structured.MonitoringParameters;
import org.eclipse.milo.opcua.stack.core.types.structured.ReadValueId;

/**
 *
 * @author Andreas
 */
public class MachineSubscriber {

    private static final AtomicLong clientHandles = new AtomicLong(1L);
    private MachineConnection mconn;

    public MachineSubscriber() {
        this("127.0.0.1", 4840);
    }

    public MachineSubscriber(String hostname, int port) {
        mconn = new MachineConnection(hostname, port);
        mconn.connect();
    }

    public void subscribe() {
        NodeId batchIdNode = new NodeId(6, "::Program:Cube.Status.Parameter[0].Value");
        NodeId quantityNode = new NodeId(6, "::Program:Cube.Status.Parameter[1].Value");
        NodeId tempNode = new NodeId(6, "::Program:Cube.Status.Parameter[2].Value");
        NodeId humidityNode = new NodeId(6, "::Program:Cube.Status.Parameter[3].Value");
        NodeId vibrationNode = new NodeId(6, "::Program:Cube.Status.Parameter[4].Value");
        NodeId producedCountNode = new NodeId(6, "::Program:Cube.Admin.ProdProcessedCount");
        NodeId failedCountNode = new NodeId(6, "::Program:Cube.Admin.ProdDefectiveCount");
        NodeId stopReasonNode = new NodeId(6, "::Program:Cube.Admin.StopReason.Id");
        NodeId stateCurrentNode = new NodeId(6, "::Program:Cube.Status.StateCurrent");
        NodeId machSpeedNode = new NodeId(6, "::Program:Cube.Status.MachSpeed");

        NodeId barleyNode = new NodeId(6, "::Program:Inventory.Barley");
        NodeId hopsNode = new NodeId(6, "::Program:Inventory.Hops");
        NodeId maltNode = new NodeId(6, "::Program:Inventory.Malt");
        NodeId wheatNode = new NodeId(6, "::Program:Inventory.Wheat");
        NodeId yeastNode = new NodeId(6, "::Program:Inventory.Yeast");

        NodeId maintenanceCounterNode = new NodeId(6, "::Program:Maintenance.Counter");
        NodeId maintenanceStateNode = new NodeId(6, "::Program:Maintenance.State");
        NodeId maintenanceTriggerNode = new NodeId(6, "::Program:Maintenance.Trigger");

        NodeId productBadNode = new NodeId(6, "::Program:product.bad");
        NodeId productGoodNode = new NodeId(6, "::Program:product.good");
        NodeId productProducedAmountNode = new NodeId(6, "::Program:product.produce_amount");
        NodeId productProducedNode = new NodeId(6, "::Program:product.produced");

        ReadValueId batchIdReadValueId = new ReadValueId(batchIdNode, AttributeId.Value.uid(), null, null);
        ReadValueId quantityReadValueId = new ReadValueId(quantityNode, AttributeId.Value.uid(), null, null);
        ReadValueId tempReadValueId = new ReadValueId(tempNode, AttributeId.Value.uid(), null, null);
        ReadValueId humidityReadValueId = new ReadValueId(humidityNode, AttributeId.Value.uid(), null, null);
        ReadValueId vibrationReadValueId = new ReadValueId(vibrationNode, AttributeId.Value.uid(), null, null);
        ReadValueId producedReadValueId = new ReadValueId(producedCountNode, AttributeId.Value.uid(), null, null);
        ReadValueId failedReadValueId = new ReadValueId(failedCountNode, AttributeId.Value.uid(), null, null);
        ReadValueId stopReasonReadValueId = new ReadValueId(stopReasonNode, AttributeId.Value.uid(), null, null);
        ReadValueId stateReadValueId = new ReadValueId(stateCurrentNode, AttributeId.Value.uid(), null, null);
        ReadValueId speedReadValueId = new ReadValueId(machSpeedNode, AttributeId.Value.uid(), null, null);

        ReadValueId barleyReadValueId = new ReadValueId(barleyNode, AttributeId.Value.uid(), null, null);
        ReadValueId hopsReadValueId = new ReadValueId(hopsNode, AttributeId.Value.uid(), null, null);
        ReadValueId maltReadValueId = new ReadValueId(maltNode, AttributeId.Value.uid(), null, null);
        ReadValueId wheatReadValueId = new ReadValueId(wheatNode, AttributeId.Value.uid(), null, null);
        ReadValueId yeastReadValueId = new ReadValueId(yeastNode, AttributeId.Value.uid(), null, null);

        ReadValueId maintenanceCounterReadValueId = new ReadValueId(maintenanceCounterNode, AttributeId.Value.uid(), null, null);
        ReadValueId maintenanceStateReadValueId = new ReadValueId(maintenanceStateNode, AttributeId.Value.uid(), null, null);
        ReadValueId maintenanceTriggerReadValueId = new ReadValueId(maintenanceTriggerNode, AttributeId.Value.uid(), null, null);

        ReadValueId productBadReadValueId = new ReadValueId(productBadNode, AttributeId.Value.uid(), null, null);
        ReadValueId productGoodReadValueId = new ReadValueId(productGoodNode, AttributeId.Value.uid(), null, null);
        ReadValueId productProducedAmountReadValueId = new ReadValueId(productProducedAmountNode, AttributeId.Value.uid(), null, null);
        ReadValueId productProducedReadValueId = new ReadValueId(productProducedNode, AttributeId.Value.uid(), null, null);

        // important: client handle must be unique per item
        UInteger batchIdClientHandle = Unsigned.uint(clientHandles.getAndIncrement());
        UInteger quantityClientHandle = Unsigned.uint(clientHandles.getAndIncrement());
        UInteger tempClientHandle = Unsigned.uint(clientHandles.getAndIncrement());
        UInteger humidityClientHandle = Unsigned.uint(clientHandles.getAndIncrement());
        UInteger vibrationClientHandle = Unsigned.uint(clientHandles.getAndIncrement());
        UInteger producedClientHandle = Unsigned.uint(clientHandles.getAndIncrement());
        UInteger failedClientHandle = Unsigned.uint(clientHandles.getAndIncrement());
        UInteger stopReasonClientHandle = Unsigned.uint(clientHandles.getAndIncrement());
        UInteger stateClientHandle = Unsigned.uint(clientHandles.getAndIncrement());
        UInteger speedClientHandle = Unsigned.uint(clientHandles.getAndIncrement());

        UInteger barleyClientHandle = Unsigned.uint(clientHandles.getAndIncrement());
        UInteger hopsClientHandle = Unsigned.uint(clientHandles.getAndIncrement());
        UInteger maltClientHandle = Unsigned.uint(clientHandles.getAndIncrement());
        UInteger wheatClientHandle = Unsigned.uint(clientHandles.getAndIncrement());
        UInteger yeastClientHandle = Unsigned.uint(clientHandles.getAndIncrement());

        UInteger maintenanceCounterClientHandle = Unsigned.uint(clientHandles.getAndIncrement());
        UInteger maintenanceStateClientHandle = Unsigned.uint(clientHandles.getAndIncrement());
        UInteger maintenanceTriggerClientHandle = Unsigned.uint(clientHandles.getAndIncrement());

        UInteger productBadClientHandle = Unsigned.uint(clientHandles.getAndIncrement());
        UInteger productGoodClientHandle = Unsigned.uint(clientHandles.getAndIncrement());
        UInteger productProducedAmountClientHandle = Unsigned.uint(clientHandles.getAndIncrement());
        UInteger productProducedClientHandle = Unsigned.uint(clientHandles.getAndIncrement());

        MonitoringParameters batchIdParameters = new MonitoringParameters(
                batchIdClientHandle,
                1000.0, // sampling interval
                null, // filter, null means use default
                Unsigned.uint(10), // queue size
                true // discard oldest
        );

        MonitoringParameters quantityParameters = new MonitoringParameters(
                quantityClientHandle,
                1000.0, // sampling interval
                null, // filter, null means use default
                Unsigned.uint(10), // queue size
                true // discard oldest
        );
        MonitoringParameters tempParameters = new MonitoringParameters(
                tempClientHandle,
                1000.0, // sampling interval
                null, // filter, null means use default
                Unsigned.uint(10), // queue size
                true // discard oldest
        );
        MonitoringParameters humidityParameters = new MonitoringParameters(
                humidityClientHandle,
                1000.0, // sampling interval
                null, // filter, null means use default
                Unsigned.uint(10), // queue size
                true // discard oldest
        );
        MonitoringParameters vibrationParameters = new MonitoringParameters(
                vibrationClientHandle,
                1000.0, // sampling interval
                null, // filter, null means use default
                Unsigned.uint(10), // queue size
                true // discard oldest
        );
        MonitoringParameters producedParameters = new MonitoringParameters(
                producedClientHandle,
                1000.0, // sampling interval
                null, // filter, null means use default
                Unsigned.uint(10), // queue size
                true // discard oldest
        );
        MonitoringParameters failedParameters = new MonitoringParameters(
                failedClientHandle,
                1000.0, // sampling interval
                null, // filter, null means use default
                Unsigned.uint(10), // queue size
                true // discard oldest
        );

        MonitoringParameters stopReasonParameters = new MonitoringParameters(
                stopReasonClientHandle,
                1000.0, // sampling interval
                null, // filter, null means use default
                Unsigned.uint(10), // queue size
                true // discard oldest
        );

        MonitoringParameters stateParameters = new MonitoringParameters(
                stateClientHandle,
                1000.0, // sampling interval
                null, // filter, null means use default
                Unsigned.uint(10), // queue size
                true // discard oldest
        );

        MonitoringParameters speedParameters = new MonitoringParameters(
                speedClientHandle,
                1000.0, // sampling interval
                null, // filter, null means use default
                Unsigned.uint(10), // queue size
                true // discard oldest
        );
        MonitoringParameters barleyParameters = new MonitoringParameters(
                barleyClientHandle,
                1000.0,
                null,
                Unsigned.uint(10),
                true
        );
        MonitoringParameters hopsParameters = new MonitoringParameters(
                hopsClientHandle,
                1000.0,
                null,
                Unsigned.uint(10),
                true
        );
        MonitoringParameters maltParameters = new MonitoringParameters(
                maltClientHandle,
                1000.0,
                null,
                Unsigned.uint(10),
                true
        );
        MonitoringParameters wheatParameters = new MonitoringParameters(
                wheatClientHandle,
                1000.0,
                null,
                Unsigned.uint(10),
                true
        );
        MonitoringParameters yeastParameters = new MonitoringParameters(
                yeastClientHandle,
                1000.0,
                null,
                Unsigned.uint(10),
                true
        );
        MonitoringParameters maintenanceCounterParameters = new MonitoringParameters(
                maintenanceCounterClientHandle,
                1000.0,
                null,
                Unsigned.uint(10),
                true
        );
        MonitoringParameters maintenanceStateParameters = new MonitoringParameters(
                maintenanceStateClientHandle,
                1000.0,
                null,
                Unsigned.uint(10),
                true
        );
        MonitoringParameters maintenanceTriggerParameters = new MonitoringParameters(
                maintenanceTriggerClientHandle,
                1000.0,
                null,
                Unsigned.uint(10),
                true
        );
        MonitoringParameters productBadParameters = new MonitoringParameters(
                productBadClientHandle,
                1000.0,
                null,
                Unsigned.uint(10),
                true
        );
        MonitoringParameters productGoodParameters = new MonitoringParameters(
                productGoodClientHandle,
                1000.0,
                null,
                Unsigned.uint(10),
                true
        );
        MonitoringParameters productProducedAmountParameters = new MonitoringParameters(
                productProducedAmountClientHandle,
                1000.0,
                null,
                Unsigned.uint(10),
                true
        );
        MonitoringParameters productProducedParameters = new MonitoringParameters(
                productProducedClientHandle,
                1000.0,
                null,
                Unsigned.uint(10),
                true
        );
        List<MonitoredItemCreateRequest> requestList = new ArrayList();
        //requestList.add(new MonitoredItemCreateRequest(batchIdReadValueId, MonitoringMode.Reporting, batchIdParameters));
        //requestList.add(new MonitoredItemCreateRequest(quantityReadValueId, MonitoringMode.Reporting, quantityParameters));
        //requestList.add(new MonitoredItemCreateRequest(tempReadValueId, MonitoringMode.Reporting, tempParameters));
//        requestList.add(new MonitoredItemCreateRequest(humidityReadValueId, MonitoringMode.Reporting, humidityParameters));
//        requestList.add(new MonitoredItemCreateRequest(vibrationReadValueId, MonitoringMode.Reporting, vibrationParameters));
//        requestList.add(new MonitoredItemCreateRequest(producedReadValueId, MonitoringMode.Reporting, producedParameters));
        //requestList.add(new MonitoredItemCreateRequest(failedReadValueId, MonitoringMode.Reporting, failedParameters));
        //requestList.add(new MonitoredItemCreateRequest(stopReasonReadValueId, MonitoringMode.Reporting, stopReasonParameters));
        requestList.add(new MonitoredItemCreateRequest(stateReadValueId, MonitoringMode.Reporting, stateParameters));
        //requestList.add(new MonitoredItemCreateRequest(speedReadValueId, MonitoringMode.Reporting, speedParameters));

        BiConsumer<UaMonitoredItem, Integer> onItemCreated = (item, id) -> item.setValueConsumer(MachineSubscriber::onSubscribeValue);

        try {
            UaSubscription subscription = mconn.getClient().getSubscriptionManager().createSubscription(1000.0).get();
            List<UaMonitoredItem> items = subscription.createMonitoredItems(TimestampsToReturn.Both, requestList, onItemCreated).get();
            for (UaMonitoredItem item : items) {
                if (item.getStatusCode().isGood()) {
                    System.out.println("item created for nodeId=" + item.getReadValueId().getNodeId());
                } else {
                    System.out.println("failed to create item for nodeId=" + item.getReadValueId().getNodeId() + " (status=" + item.getStatusCode() + ")");
                }
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(MachineSubscriber.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExecutionException ex) {
            Logger.getLogger(MachineSubscriber.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void onSubscribeValue(UaMonitoredItem item, DataValue value) {
        System.out.println("Item: " + item.getReadValueId().getNodeId() + " Value: " + value.getValue());
    }

}
