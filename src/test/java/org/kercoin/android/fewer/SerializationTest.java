package org.kercoin.android.fewer;

import static org.fest.assertions.Assertions.assertThat;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.Test;

public class SerializationTest {

	@Test
	public void shouldBeSerializable() throws IOException, ClassNotFoundException {
		Channel c = new Channel("k1");
		Stream s1 = new Stream("BBC2", "http://some.co.uk/bbc2", new Option[0]);
		Stream s2 = new Stream("BBC2 HD", "http://some.co.uk/bbc2_hd", new Option[0]);
		c.add(s1);
		c.add(s2);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(c);
		ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
		ObjectInputStream ois = new ObjectInputStream(bais);
		Channel copy = (Channel) ois.readObject();
		assertThat(copy).isNotNull();
		assertThat(copy.getName()).isEqualTo(c.getName());
		assertThat(copy.getStreams()).hasSize(2);
		assertThat(copy.getStreams()[0].getName()).isEqualTo(s1.getName());
		assertThat(copy.getStreams()[0].getSource()).isEqualTo(s1.getSource());
	}
}
