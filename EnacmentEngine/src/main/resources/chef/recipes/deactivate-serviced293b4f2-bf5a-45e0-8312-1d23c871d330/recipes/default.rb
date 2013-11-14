#
# Cookbook Name:: generic-jar-service-template
# Recipe:: default
#
# Copyright 2012, YOUR_COMPANY_NAME
#
# All rights reserved - Do Not Redistribute
#

##########################################################################
#                  														 #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                  														 #
# All ocurrences of $ NAME must be replaced with the actual service name #
#            before uploading the recipe to the chef-server              #
#                  														 #
##########################################################################

ruby_block "remove-serviced293b4f2-bf5a-45e0-8312-1d23c871d330" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[serviced293b4f2-bf5a-45e0-8312-1d23c871d330::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/d293b4f2-bf5a-45e0-8312-1d23c871d330.war]", :immediately
end

ruby_block "remove-serviced293b4f2-bf5a-45e0-8312-1d23c871d330" do
  block do
  	node.run_list.remove("recipe[serviced293b4f2-bf5a-45e0-8312-1d23c871d330::war]")
  end
  only_if { node.run_list.include?("recipe[serviced293b4f2-bf5a-45e0-8312-1d23c871d330::war]") }
end

ruby_block "remove-deactivate-serviced293b4f2-bf5a-45e0-8312-1d23c871d330" do
  block do
    node.run_list.remove("recipe[deactivate-serviced293b4f2-bf5a-45e0-8312-1d23c871d330]")
  end
  only_if { node.run_list.include?("recipe[deactivate-serviced293b4f2-bf5a-45e0-8312-1d23c871d330]") }
end
