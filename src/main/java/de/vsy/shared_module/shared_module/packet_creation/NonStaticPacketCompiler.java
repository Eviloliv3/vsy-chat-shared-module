package de.vsy.shared_module.shared_module.packet_creation;

import de.vsy.shared_transmission.shared_transmission.packet.Packet;
import de.vsy.shared_transmission.shared_transmission.packet.PacketBuilder;
import de.vsy.shared_transmission.shared_transmission.packet.content.PacketContent;
import de.vsy.shared_transmission.shared_transmission.packet.property.PacketPropertiesBuilder;
import de.vsy.shared_transmission.shared_transmission.packet.property.communicator.CommunicationEndpoint;

public
class NonStaticPacketCompiler {

    /*Der Anbieter für die Absenderinstanz*/
    private OriginatingEntityProvider originator;
    /*Liefert den Identifikator für Paketdaten*/
    private ContentIdentificationProvider contentIdentificator;

    public
    void addOriginatorEntityProvider (OriginatingEntityProvider originator) {
        this.originator = originator;
    }

    public
    void addContentIdentificator (
            ContentIdentificationProvider contentIdentificator) {
        this.contentIdentificator = contentIdentificator;
    }

    /**
     * Erstellt ein versandfertiges Antwortpaket mit den angegebenen Daten. Der
     * Absender steht für jede paketerstellende Instanz fest. Als Empfänger wird der
     * Absender des Anfragepaket verwendet. Der Datenidentifikator sollte von den
     * Daten abgeleitet werden.
     *
     * @param data die Paketdaten (PacketContent)
     *
     * @return das fertige Paket
     *
     * @throws NullPointerException  wenn einer der Parameter, kein Absender oder
     *                               kein Anfragehashwert vorhanden ist.
     * @throws IllegalStateException wenn kein Anbieter für die Absenderinstanz oder
     *                               die Identifikationsbestimmung angegeben wurden
     */
    public
    Packet createResponse (PacketContent data, Packet request) {
        var properties = request.getPacketProperties();
        var recipient = properties.getSenderEntity();
        var requestPacketHash = request.getPacketHash();

        return compilePacket(recipient, data, requestPacketHash);
    }

    /**
     * Hilfsfunktion die übergebenen Daten in einem neuen Paket zusammenführt.
     *
     * @param recipient der Empfänger (CommunicationEndpoint)
     * @param data die Paketdaten (PacketContent)
     * @param requestPacketHash der Anfragehashwert (String)
     *
     * @return das neue Paket
     *
     * @throws IllegalStateException wenn kein Anbieter für die Absenderinstanz oder
     *                               die Identifikationsbestimmung angegeben wurden
     */
    private
    Packet compilePacket (CommunicationEndpoint recipient, PacketContent data,
                          String requestPacketHash) {
        var sender = originator.getOriginatorEntity();
        var props = new PacketPropertiesBuilder().withSender(sender)
                                                 .withRecipient(recipient)
                                                 .withIdentifier(
                                                         contentIdentificator.getContentIdentifier(
                                                                 data))
                                                 .build();
        return new PacketBuilder().withContent(data)
                                  .withProperties(props)
                                  .withRequestPacket(requestPacketHash)
                                  .build();
    }

    /**
     * Erstellt ein versandfertiges Anfragepaket mit dem angegebenen Empfänger und
     * den angegebenen Daten. Der Absender steht für jede paketerstellende Instanz
     * fest. Der Datenidentifikator sollte von den Daten abgeleitet werden.
     *
     * @param recipient der Empfänger (CommunicationEndpoint)
     * @param data die Paketdaten (PacketContent)
     *
     * @return das fertige Paket
     *
     * @throws NullPointerException  wenn einer der Parameter nicht vorhanden ist
     * @throws IllegalStateException wenn kein Anbieter für die Absenderinstanz oder
     *                               die Identifikationsbestimmung angegeben wurden
     */
    public
    Packet createRequest (CommunicationEndpoint recipient, PacketContent data) {
        return compilePacket(recipient, data, null);
    }

    /**
     * Erstellt ein versandfertiges Antwortpaket mit dem angegebenen Empfänger und
     * den angegebenen Daten. Der Absender steht für jede paketerstellende Instanz
     * fest. Der Datenidentifikator kann von den Daten abgeleitet werden. Der
     * Anfragehashwert wird aus dem Anfragepaket geholt.
     *
     * @param recipient der Empfänger (CommunicationEndpoint)
     * @param data die Paketdaten (PacketContent)
     * @param request das zu beantwortende Paket (Packet)
     *
     * @return das fertige Paket
     *
     * @throws NullPointerException  wenn einer der Parameter nicht vorhanden ist
     * @throws IllegalStateException wenn kein Anbieter für die Absenderinstanz oder
     *                               die Identifikationsbestimmung angegeben wurden
     */
    public
    Packet createCustomRecipientResponse (CommunicationEndpoint recipient,
                                          PacketContent data, Packet request) {
        var requestHash = request.getRequestPacketHash();

        return compilePacket(recipient, data, requestHash);
    }
}
