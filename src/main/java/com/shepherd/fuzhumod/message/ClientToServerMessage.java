package com.shepherd.fuzhumod.message;

import java.util.UUID;

import com.shepherd.fuzhumod.base.Config;
import com.shepherd.fuzhumod.entity.TileEntityHunDunTable;
import com.shepherd.fuzhumod.gui.GuiHunDunTable;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class ClientToServerMessage  implements IMessage{

	private NBTTagCompound dataTag;
	
	public ClientToServerMessage() {}
	public ClientToServerMessage(UUID uuid, String messageType, NBTTagCompound messageDate) {
		this.dataTag = new NBTTagCompound();
		dataTag.setString("UUID", uuid.toString());
		dataTag.setString("MessageType", messageType);
		dataTag.setTag("MessageDate", messageDate);
	}
	
	@Override
	public void fromBytes(ByteBuf buf){
		dataTag = ByteBufUtils.readTag(buf);
	}

	@Override
	public void toBytes(ByteBuf buf){
		ByteBufUtils.writeTag(buf, dataTag);
	}

	public static class Handler implements IMessageHandler<ClientToServerMessage, IMessage>{
		@Override
		public IMessage onMessage(final ClientToServerMessage message, MessageContext ctx){
			if (ctx.side == Side.SERVER){
				final EntityPlayerMP player = ctx.getServerHandler().playerEntity;
				final World world = player.getEntityWorld();
				final String uuid = message.dataTag.getString("UUID");
				final String messageType = message.dataTag.getString("MessageType");
				final NBTTagCompound messageDate = (NBTTagCompound)message.dataTag.getTag("MessageDate");
				
				if(GuiHunDunTable.GUINAME.equals(messageType)) {
					int dimensionId = messageDate.getInteger("DimensionId");
					int x = messageDate.getInteger("X");
					int y = messageDate.getInteger("Y");
					int z = messageDate.getInteger("Z");
					if(dimensionId == world.provider.dimensionId) {
						TileEntity tileEntity = world.getTileEntity(x, y, z);
						if(null != tileEntity && tileEntity instanceof TileEntityHunDunTable) {
							((TileEntityHunDunTable)tileEntity).execute(player);
						}else {
							player.addChatMessage(new ChatComponentText(I18n.format(Config.MODID + ".guiHunDunTable.error2", new Object[] {null == tileEntity ? "NULL" : tileEntity.getClass().getSimpleName()})));
						}
					}else {
						player.addChatMessage(new ChatComponentText(I18n.format(Config.MODID + ".guiHunDunTable.error1", new Object[] {dimensionId, world.provider.dimensionId})));
					}
				}else {
					player.addChatMessage(new ChatComponentText(I18n.format(Config.MODID + ".clientToServerMessage.error1", new Object[] {messageType})));
				}
			}
			return null;
//			MinecraftServer.getServer().addChatMessage(new ChatComponentText("werwerwerwe"));
//			MinecraftServer.getServer().addChatMessage(new ChatComponentText("kkkkkkkkkkkk"));
		}
	}
}
