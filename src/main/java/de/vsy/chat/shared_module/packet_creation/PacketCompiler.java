package de.vsy.chat.shared_module.packet_creation;

import de.vsy.chat.shared_transmission.packet.Packet;
import de.vsy.chat.shared_transmission.packet.PacketBuilder;
import de.vsy.chat.shared_transmission.packet.content.PacketContent;
import de.vsy.chat.shared_transmission.packet.property.PacketPropertiesBuilder;
import de.vsy.chat.shared_transmission.packet.property.communicator.CommunicationEndpoint;

public
class PacketCompiler {

    /*Der Anbieter für die Absenderinstanz*/
    private static OriginatingEntityProvider originatorProvider;
    /*Liefert den Identifikator für Paketdaten*/
    private static ContentIdentificationProvider contentIdentificator;

    private
    PacketCompiler () {}

    public static
    void addOriginatorEntityProvider (OriginatingEntityProvider originator) {
        PacketCompiler.originatorProvider = originator;
    }

    public static
    OriginatingEntityProvider getOriginatorEntityProvider () {
        return PacketCompiler.originatorProvider;
    }

    public static
    void addContentIdentificator (ContentIdentificationProvider identificator) {
        PacketCompiler.contentIdentificator = identificator;
    }

    /**
     * Erstellt ein versandfertiges Antwortpaket mit den angegebenen Daten.Der
     * Absender steht für jede paketerstellende Instanz fest. Als Empfänger wird der
     * Absender des Anfragepaket verwendet. Der Datenidentifikator sollte von den
     * Daten abgeleitet werden.
     *
     * @param data die Paketdaten (PacketContent)
     * @param request the packet to respond to
     *
     * @return das fertige Paket
     *
     * @throws NullPointerException  wenn einer der Parameter, kein Absender oder
     *                               kein Anfragehashwert vorhanden ist.
     * @throws IllegalStateException wenn kein Anbieter für die Absenderinstanz oder
     *                               die Identifikationsbestimmung angegeben wurden
     */
    public static
    Packet createResponse (PacketContent data, Packet request) {
        var properties = request.getPacketProperties();
        var sender = PacketCompiler.originatorProvider.getOriginatorEntity();
        var recipient = properties.getSenderEntity();
        var requestPacketHash = request.getPacketHash();

        return compilePacket(sender, recipient, data, requestPacketHash);
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
    private static
    Packet compilePacket (CommunicationEndpoint sender,
                          CommunicationEndpoint recipient, PacketContent data,
                          String requestPacketHash) {
        var props = new PacketPropertiesBuilder().withSender(sender)
                                                 .withRecipient(recipient)
                                                 .withIdentifier(
                                                         PacketCompiler.contentIdentificator.getContentIdentifier(
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
    public static
    Packet createRequest (CommunicationEndpoint recipient, PacketContent data) {
        var sender = PacketCompiler.originatorProvider.getOriginatorEntity();
        return compilePacket(sender, recipient, data, null);
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
    public static
    Packet createFollowUpRequest (CommunicationEndpoint recipient,
                                  PacketContent data, Packet request) {
        var sender = request.getPacketProperties().getSenderEntity();
        var requestHash = request.getRequestPacketHash();

        return compilePacket(sender, recipient, data, requestHash);
    }
}
