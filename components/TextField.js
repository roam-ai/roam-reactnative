import React from 'react';
import {Text, StyleSheet, View} from 'react-native';

export const TextField = (props) => {
  return (
    <View style={styles.container}>
      <Text numberOfLines={1} style={styles.text}>
        {props.children}
      </Text>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    borderRadius: 4,
    borderWidth: 2,
    paddingHorizontal: 15,
    paddingVertical: 5,
    marginTop: 10,
    marginLeft: 5,
    borderColor: '#bababa',
  },
  text: {
    fontWeight: 'bold',
    color: '#bababa',
    fontSize: 18,
  },
});
